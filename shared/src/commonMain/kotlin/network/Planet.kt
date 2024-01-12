package network

import data.FilmReqRes
import data.Planet
import data.PlanetReqRes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PlanetAPI {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
//                contentType = ContentType.Text.Plain, // because Github is not returning an 'application/json' header
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
        }
    }
    suspend fun getAllPlanets(): PlanetReqRes {
        try {
            return httpClient.get("https://swapi.dev/api/planets").body()
        } catch (err:Exception) {
            throw err;
        }
    }

    suspend fun getPlanetByName(name:String): PlanetReqRes {
        try {
            return httpClient.get("https://swapi.dev/api/planets/?search=$name").body()
        } catch (err:Exception) {
            return PlanetReqRes(count = 0, results = listOf())
            //throw err;
        }
    }

    suspend fun getPlanet(id:Int): Planet {
        try {
            return httpClient.get("https://swapi.dev/api/planets/$id").body()
        } catch (err:Exception) {
            throw err;
        }
    }

    suspend fun getPlanetReq(req:String): Planet {
        try {
            return httpClient.get(req).body()
        } catch (err:Exception) {
            throw err;
        }
    }
}