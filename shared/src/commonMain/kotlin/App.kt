import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Question
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import network.QuizRepository

private val repository = QuizRepository()

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val questions = repository.questionState.collectAsState()
    PreComposeApp {
        val navigator = rememberNavigator()
        MaterialTheme {
            NavHost(
                navigator = navigator,
                initialRoute = "/welcome"
            ) {
                scene(route = "/welcome") {
                    WelcomeScreen(navigator);
                }
                scene(route = "/quizz") {
                    if(questions.value.isNotEmpty()) {
//                      QuestionScreen(navigator,questions.value)
                        guessMovieScreen();
                    }
                }
                scene(route = "/score/{score}") { backStackEntry ->
                    backStackEntry.path<String>("score")?.let { score ->
                        ScoreScreen(navigator,score,questions.value.size);
                    }
                }
            }
        }
    }
}

var searchResults by mutableStateOf<List<String>>(emptyList()) //liste de réponses fausses de l'utilisateur
var searchText by mutableStateOf<String>("") //valeur entrée par l'utilisateur
var hintBlocks by mutableStateOf<List<String>>(listOf("Indice 1","Indice 2","Indice 3","Indice 4","Indice 5", "Indice 6", "Indice 7", "Indice 8")) //bloc de texte pour les indices

@Composable
fun guessMovieScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF000080))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, color = Color.White, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color.White),
                placeholder = { Text("Taper le nom du film") },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        search(searchText)
                        searchText = ""
                    }
                )
            )
        }

        hintBlocks.take(searchResults.size+3).forEach { hintBlock ->
            Text(
                text = hintBlock,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.Green)
            )
        }

        if (searchResults.isNotEmpty()) {
            Text(
                text = "Réponses :",
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000080))
        ) {
            items(searchResults.reversed()) { result ->
                Text(
                    text = result,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


fun search(searchText: String?) {
    val filmStarWars = "A New Hope"
    if (!searchText.isNullOrBlank() && !searchText.equals(filmStarWars, ignoreCase = true)) {
        println("Ce n'est pas le bon film : $searchText")

        if (hintBlocks.size < 5+3) { //A chaque fois que l'utilisateur rentre une mauvaise réponse alors
            hintBlocks += "Nouvel indice pour \"$searchText\"" //on dévoile un indice (il faut faire la logique de récupération)
        }
        searchResults = (searchResults + searchText).distinct()
    }
}


@Composable
internal fun WelcomeScreen(navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(text = "Quizz", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "A simple Quizz to test")
                Button(onClick = {
                    navigator.navigate(route = "/quizz")
                }) {
                    Text(text = "Start Quizz")
                }
            }
        }
    }
}

@Composable
internal fun ScoreScreen(navigator: Navigator, score: String, size: Int) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(backgroundColor = Color.Green) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp,2.dp,10.dp,10.dp)
            ) {
                Text(text = "score")
                Text(text = "$score/$size", fontWeight = FontWeight.SemiBold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
                Button(onClick = {
                    navigator.navigate(route = "/quizz")
                }) {
                    Text(text = "Retake the Quizz")
                }
            }
        }
    }
}

@Composable
internal fun QuestionScreen(navigator: Navigator,questions : List<Question>) {
    var score by remember { mutableStateOf(0) }
    var answerId by remember { mutableStateOf(-1) }
    var currentIndex by remember { mutableStateOf(0) }
    Scaffold(
        backgroundColor = Color.DarkGray,
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        bottomBar = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    modifier = Modifier.padding(bottom = 10.dp),
                    onClick = {
                        if (answerId > -1) {
                            if (answerId == questions[currentIndex].correctAnswerId && score < questions.size) {
                                score++
                            }
                            if (currentIndex + 1 >= questions.size) {
                                navigator.navigate(route = "/score/$score")
                            }
                            else {
                                currentIndex++
                                answerId = -1
                            }
                        }
                    }
                ) {
                    Text(text = "Next")
                }
                LinearProgressIndicator(progress = currentIndex.toFloat()/questions.size.toFloat(), modifier = Modifier.fillMaxWidth().height(20.dp))
            }
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Card(modifier = Modifier.padding(top = 20.dp, bottom = 50.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = questions[currentIndex].label, fontSize = 20.sp)
                }
            }
            questions[currentIndex].answers.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        onClick = {answerId = it.id},
                        selected = answerId == it.id
                    )
                    Text(it.label)
                }
            }
        }
    }
}

expect fun getPlatformName(): String