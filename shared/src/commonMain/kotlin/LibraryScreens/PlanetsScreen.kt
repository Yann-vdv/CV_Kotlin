package LibraryScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Film
import data.Planet
import moe.tlaster.precompose.navigation.Navigator
import network.PlanetRepository

var searchValue by mutableStateOf<String>("")

@Composable
fun PlanetsScreen(navigator: Navigator, planets: List<Planet?>) {

    fun search(text:String) {
        println("start search planet")
        val repositoryPlanet = PlanetRepository()
        repositoryPlanet.getPlanetByName(text);
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(bottom = 5.dp))
        TextField(
            value = searchValue,
            onValueChange = {
                searchValue = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, color = Color.White, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.White),
            placeholder = { Text("Name of a planet : ") },
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
                    search(searchValue)
                    searchValue = ""
                }
            )
        )
        Spacer(modifier = Modifier.padding(bottom = 5.dp))
        if (planets.size > 0) {
            planets.filter { planet: Planet? -> planet?.name?.contains(searchValue, ignoreCase = true) ?: false }
                .forEach { planet: Planet? ->
                    if (planet != null) {
                        Card {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${planet.name}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(text = "Population : ${planet.population}")
                                Text(text = "Climate : ${planet.climate}")
                                Text(text = "Terrain : ${planet.terrain}")
                                Text(text = "Diameter : ${planet.diameter} km")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 5.dp))
                }
        } else {
            Card(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
            ){
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "planets are loading")
                }
            }
        }
        Button(onClick = {
            navigator.navigate(route = "/welcome")
        }) {
            Text(text = "Go back")
        }
    }
}