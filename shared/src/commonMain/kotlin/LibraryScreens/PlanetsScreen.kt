package LibraryScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Film
import data.Planet
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun PlanetsScreen(navigator: Navigator, planets: List<Planet?>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (planets.size > 0) {
            planets.forEach { planet: Planet? ->
                if (planet != null) {
                    Card(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Column {
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
            ) {
                Text(text = "planets are loading")
            }
        }
        Button(onClick = {
            navigator.navigate(route = "/welcome")
        }) {
            Text(text = "Go back")
        }
    }
}