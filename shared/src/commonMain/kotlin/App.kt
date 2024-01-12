import LibraryScreens.MoviesScreen
import LibraryScreens.PlanetsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.zIndex
import data.Film
import data.Planet
import kotlinx.coroutines.delay
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import network.FilmRepository
import network.PlanetRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi

private val repository = FilmRepository()
private val repositoryPlanet = PlanetRepository()

expect fun getPlatformName(): String
@Composable
internal fun WelcomeScreen(navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(text = "StarWars Guesser", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.White)
        if (getPlatformName() == "Desktop") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Star Wars Quizz",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Button(onClick = {
                            navigator.navigate(route = "/quizz")
                        }) {
                            Text(text = "Guess the movie")
                        }
                        Button(onClick = {
                            navigator.navigate(route = "/planets")
                        }) {
                            Text(text = "Guess the planet")
                        }
                    }
                }
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Star Wars Library",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Button(onClick = {
                            navigator.navigate(route = "/moviesRepo")
                        }) {
                            Text(text = "Consult movies")
                        }
                        Button(onClick = {
                            navigator.navigate(route = "/planetsRepo")
                        }) {
                            Text(text = "Consult planets")
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Star Wars Quizz",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Button(onClick = {
                            navigator.navigate(route = "/quizz")
                        }) {
                            Text(text = "Guess the movie")
                        }
                        Button(onClick = {
                            navigator.navigate(route = "/planets")
                        }) {
                            Text(text = "Guess the planet")
                        }
                    }
                }
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Star Wars Library",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Button(onClick = {
                            navigator.navigate(route = "/moviesRepo")
                        }) {
                            Text(text = "Consult movies")
                        }
                        Button(onClick = {
                            navigator.navigate(route = "/planetsRepo")
                        }) {
                            Text(text = "Consult planets")
                        }
                    }
                }
            }
        }
    }
}


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun App() {
        val films = repository.filmsState.collectAsState()
        val planets = repositoryPlanet.planetsState.collectAsState()

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
                        if(films.value.isNotEmpty()) {
                            randomMovie(films.value)
                            guessMovieScreen(navigator, films.value)
                        }
                    }
                    scene(route = "/planets") {
                        if(planets.value.isNotEmpty()) {
                            randomPlanet(planets.value)
                            guessPlanetScreen(navigator)
                        }
                    }
                    scene(route = "/EndScreen/{isSucess}") {
                        it.path<Boolean>("isSucess")?.let {
                           EndScreen(navigator, it, films.value ) }
                    }
                    scene(route = "/EndScreenPlanet/{isSucess}") {
                        it.path<Boolean>("isSucess")?.let {
                            EndScreenPlanet(navigator, it, planets.value ) }
                    }
                    scene(route = "/moviesRepo") {
                        MoviesScreen(navigator,films.value);
                    }
                    scene(route = "/planetsRepo") {
                        PlanetsScreen(navigator,planets.value);
                    }
                }
            }
        }
    }

    var searchResults by mutableStateOf<List<String>>(emptyList()) //liste de réponses fausses de l'utilisateur
    var searchText by mutableStateOf<String>("") //valeur entrée par l'utilisateur
    var hintBlocks by mutableStateOf<List<String>>(emptyList()) //bloc de texte pour les indices
    var currentRandomFilm: Film? = null
    var isSucess: Boolean = false;

    @Composable
    fun guessMovieScreen(navigator: Navigator, films: List<Film?>) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
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
                    placeholder = { Text("Type the name of the movie : ") },
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
                            search(searchText, navigator)
                            searchText = ""
                        }
                    )
                )
            }


            hintBlocks.take(searchResults.size + 2).forEach { hintBlock ->
                val limitedText = hintBlock.take(150)
                Text(
                    text = limitedText,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }

            if (searchResults.isNotEmpty()) {
                Text(
                    text = "Answers :",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                items(searchResults.reversed()) { result ->
                        Text(
                            text = result,
                            color = Color.Yellow,
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


var hintBlocksGenerated = false

fun randomMovie(films: List<Film?>) {
    if (!hintBlocksGenerated && films.isNotEmpty()) {
        println("movies : ${films.size}")
        currentRandomFilm = films.random()
        println("Selected movie : ${currentRandomFilm?.title ?: "No movie found"}")

        hintBlocks += "Release Date: ${currentRandomFilm?.release_date ?: "N/A"}"
        hintBlocks += "Director: ${currentRandomFilm?.director ?: "N/A"}"
        hintBlocks += "Edited: ${currentRandomFilm?.edited ?: "N/A"}"
        hintBlocks += "producer: ${currentRandomFilm?.producer ?: "N/A"}"
        hintBlocks += "Opening Crawl: ${currentRandomFilm?.opening_crawl ?: "N/A"}"
        hintBlocksGenerated = true
    }
}

fun reset(navigator: Navigator, films: List<Film?>) {
    hintBlocksGenerated = false;
    hintBlocks = emptyList();
    currentRandomFilm = null;
    randomMovie(films)
    hintBlocksGenerated = false;
    hintBlocks = emptyList();
    searchResults = emptyList();
    navigator.navigate(route = "/welcome");
}

    fun search(searchText: String?, navigator: Navigator) {
        if (currentRandomFilm != null && searchText.equals(currentRandomFilm?.title, ignoreCase = true)) {
            println("Bravo! Vous avez trouvé le film.")
            isSucess = true;
            navigator.navigate(route = "/EndScreen/$isSucess")
        } else {
            searchResults = (searchResults + searchText).distinct() as List<String>
            println("Mauvaise réponse, essayez encore.")
        }
        if (hintBlocks.size < 5) {
            hintBlocks += "Nouvel indice pour \"$searchText\""
        }
        if(searchResults.size > 3)
        {
            isSucess = false;
            navigator.navigate(route = "/EndScreen/$isSucess")
        }
    }


@Composable
internal fun EndScreen(navigator: Navigator, isSucess: Boolean, films: List<Film?>) {
    var offsetY by remember { mutableStateOf(0.dp) }

    LaunchedEffect(true) {
        while (offsetY < 300.dp) {
            delay(16L)
            offsetY -= 1.dp
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Movie to find : ${currentRandomFilm?.title?.uppercase()}",
            textAlign = TextAlign.Center,
            color = Color.Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .fillMaxHeight()
                .zIndex(3f)
                .align(Alignment.Start)
        )
        if (isSucess) {
            Text(
                text = "Congratulation you found the movie",
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .fillMaxHeight()
                    .zIndex(3f)
                    .align(Alignment.Start)
            )
        }
        if (!isSucess) {
            Text(
                text = "You failed ! ",
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .fillMaxHeight()
                    .zIndex(3f)
                    .align(Alignment.Start)
            )
        }

        Text(
            text = "${currentRandomFilm?.opening_crawl}",
            textAlign = TextAlign.Center,
            color = Color.Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            modifier = Modifier
                .padding(0.dp)
                .offset(y = offsetY)
        )
        Button(onClick = {
            reset(navigator, films);
        }) {
            Text(text = "Retake the Quizz")
        }
    }
}
    var searchResultsPlanet by mutableStateOf<List<String>>(emptyList()) //liste de réponses fausses de l'utilisateur
    var searchTextPlanet by mutableStateOf<String>("") //valeur entrée par l'utilisateur
    var hintBlocksPlanet by mutableStateOf<List<String>>(emptyList()) //bloc de texte pour les indices
    var currentRandomPlanet: Planet? = null
    var isSucessPlanet: Boolean = false;



    @Composable
    fun guessPlanetScreen(navigator: Navigator) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = searchTextPlanet,
                    onValueChange = {
                        searchTextPlanet = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, color = Color.White, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color.White),
                    placeholder = { Text("Type the name of the planet : ") },
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
                            searchPlanet(searchTextPlanet, navigator)
                            searchTextPlanet = ""
                        }
                    )
                )
            }

            hintBlocksPlanet.take(searchResultsPlanet.size + 2).forEach { hintBlocksPlanet ->
                val limitedText = hintBlocksPlanet
                Text(
                    text = limitedText,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }

            if (searchResultsPlanet.isNotEmpty()) {
                Text(
                    text = "Answers :",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                items(searchResultsPlanet.reversed()) { result ->
                    Text(
                        text = result,
                        color = Color.Yellow,
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


    var hintBlocksGeneratedPlanet = false

    fun randomPlanet(planets: List<Planet?>) {
        if (!hintBlocksGeneratedPlanet && planets.isNotEmpty()) {
            println("Planets : ${planets.size}")

            currentRandomPlanet = planets.random()

            println("Selected planet : ${currentRandomPlanet?.name ?: "No planet found"}")


            hintBlocksPlanet += "Climate: ${currentRandomPlanet?.climate ?: "N/A"}"
            hintBlocksPlanet += "Population: ${currentRandomPlanet?.population ?: "N/A"}"
            hintBlocksPlanet += "Diameter: ${currentRandomPlanet?.diameter ?: "N/A"}"
            hintBlocksPlanet += "Terrain: ${currentRandomPlanet?.terrain ?: "N/A"}"
            hintBlocksPlanet += "Surface_water: ${currentRandomPlanet?.surface_water ?: "N/A"}"

            hintBlocksGeneratedPlanet = true
        }
    }

    fun resetPlanet(navigator: Navigator, planets: List<Planet?>) {
        hintBlocksGeneratedPlanet = false;
        hintBlocksPlanet = emptyList();
        currentRandomPlanet = null;
        randomPlanet(planets)
        hintBlocksGeneratedPlanet = false;
        hintBlocksPlanet = emptyList();
        searchResultsPlanet = emptyList();
        navigator.navigate(route = "/welcome");
    }

    fun searchPlanet(searchText: String?, navigator: Navigator) {
        if (currentRandomPlanet != null && searchText.equals(currentRandomPlanet?.name, ignoreCase = true)) {
            println("Bravo! Vous avez trouvé la planet.")
            isSucessPlanet = true;
            navigator.navigate(route = "/EndScreen/$isSucess")
        } else {
            searchResultsPlanet = (searchResultsPlanet + searchTextPlanet).distinct() as List<String>
            println("Mauvaise réponse, essayez encore.")
        }
        if (hintBlocksPlanet.size < 5) {
            hintBlocksPlanet += "Nouvel indice pour \"$searchText\""
        }
        if(searchResultsPlanet.size > 3)
        {
            isSucessPlanet = false;
            navigator.navigate(route = "/EndScreenPlanet/$isSucess")
        }
    }

@Composable
internal fun EndScreenPlanet(navigator: Navigator, isSucess: Boolean, planets: List<Planet?>) {
    var offsetY by remember { mutableStateOf(0.dp) }

    LaunchedEffect(true) {
        while (offsetY < 300.dp) {
            delay(16L)
            offsetY -= 1.dp
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Planet to find : ${currentRandomPlanet?.name?.uppercase()}",
            textAlign = TextAlign.Center,
            color = Color.Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,

            modifier = Modifier.padding(8.dp, 10.dp)
                .zIndex(2f)
                .background(Color.Black)
                .fillMaxHeight()
                .fillMaxWidth(),
        )
        if (isSucess) {
            Text(
                text = "Congratulation you found the planet",
                textAlign = TextAlign.Center,
                color = Color.Yellow,
                modifier = Modifier.padding(8.dp)
            )
        }
        if (!isSucess) {
            Text(
                text = "You failed ! ",
                textAlign = TextAlign.Center,
                color = Color.Yellow,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(onClick = {
            resetPlanet(navigator, planets);
        }) {
            Text(text = "Retake the Quizz")
        }
    }
}