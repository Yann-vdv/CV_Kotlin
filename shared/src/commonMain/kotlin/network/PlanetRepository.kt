package network

import data.Film
import data.Planet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlanetRepository {
    private val planetAPI = PlanetAPI()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var _planetsState=  MutableStateFlow(listOf<Planet?>())
    val planetsState = _planetsState

    init {
        updatePlanets()
    }

    private suspend fun fetchPlanets(): List<Planet?> = planetAPI.getAllPlanets().results;

    suspend fun getPlanetByName(name:String): Boolean = planetAPI.getPlanetByName(name).count == 1;

    suspend fun getPlanet(id:Int): Planet = planetAPI.getPlanet(id);

    suspend fun getPlanetReq(req:String): Planet = planetAPI.getPlanetReq(req);

    private fun updatePlanets(){

        coroutineScope.launch {
            try {
                _planetsState.update { fetchPlanets() }
            } catch (err:Exception) {
                println(err)
                _planetsState.update { cache }
            }
        }
    }

    val cache = listOf(
        Planet(
            name = "Tatooine",
            rotation_period = 23,
            orbital_period = 304,
            diameter = 10465,
            climate = "arid",
            gravity = "1 standard",
            terrain = "desert",
            surface_water = "1",
            population = "200000",
            residents = listOf(
                "https://swapi.dev/api/people/1/",
                "https://swapi.dev/api/people/2/",
                "https://swapi.dev/api/people/4/",
                "https://swapi.dev/api/people/6/",
                "https://swapi.dev/api/people/7/",
                "https://swapi.dev/api/people/8/",
                "https://swapi.dev/api/people/9/",
                "https://swapi.dev/api/people/11/",
                "https://swapi.dev/api/people/43/",
                "https://swapi.dev/api/people/62/"
            ),
            films = listOf(
                "https://swapi.dev/api/films/1/",
                "https://swapi.dev/api/films/3/",
                "https://swapi.dev/api/films/4/",
                "https://swapi.dev/api/films/5/",
                "https://swapi.dev/api/films/6/"
            ),
            created = "2014-12-09T13:50:49.641000Z",
            edited = "2014-12-20T20:58:18.411000Z",
            url = "https://swapi.dev/api/planets/1/"
        )
    )
}