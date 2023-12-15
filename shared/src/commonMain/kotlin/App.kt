import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.CvData
import data.ParData
import data.Question
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator

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
                        QuestionScreen(navigator,questions.value)
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
//        CV(CvData());
}

@Composable
@OptIn(ExperimentalResourceApi::class)
internal fun CV(data: CvData) {
    Row(modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Column(modifier = Modifier.width(250.dp)) {
//                        AsyncImage(
//                            model = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
//                            contentDescription = "Photo de profil"
//                        )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(250.dp)
            ) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    contentDescription = "Compose Multiplatform icon",
                    modifier = Modifier.size(140.dp)
                )
                Text(
                    data.lastName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Text(data.firstName, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(data.age.toString() + " ans", fontWeight = FontWeight.SemiBold)
                Text(data.subject, fontWeight = FontWeight.SemiBold)
            }
            Text("PROFIL", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(data.profilDesc)
            Text("LANGUES", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            data.languages.forEach { langue ->
                Language(
                    title = langue.name,
                    score = langue.score
                )
            }
            Text("CONTACT", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("TELEPHONE :", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(data.phone)
            Text("E-MAIL :", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(data.email)
        }
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Column(modifier = Modifier.padding(end = 20.dp)) {
            Text(
                "EXPERIENCES PROFESSIONNELLES",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            data.paragraph.forEach { para -> Par(para) }
            Text("FORMATIONS", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            data.paragraph2.forEach { para -> Par(para) }
        }
    }
}
@Composable
internal fun Par(data: ParData) {
    Spacer(modifier = Modifier.padding(top = 10.dp))
    Column {
        Text(text = data.title, fontWeight = FontWeight.Bold)
        Text(data.date)
        Text(data.description)
    }
}
@Composable
internal fun Language(title: String, score: Int) {
    Row {
        Text(text = title, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.padding(end = 5.dp))
        if (score == 1) Text(text = "X")
        else if (score==2) Text(text = "XX")
        else if (score==3) Text(text = "XXX")
        else if (score==4) Text(text = "XXXX")
        else if (score==5) Text(text = "XXXXX")
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