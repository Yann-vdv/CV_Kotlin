package network

import data.Answer
import data.Question
import data.Quiz
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CacheQuizAPI {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                contentType = ContentType.Text.Plain, // because Github is not returning an 'application/json' header
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
        }
    }
    suspend fun getAllQuestions(): Quiz {
        return Quiz(listOf(
            Question(id = 0, correctAnswerId = 0, label = "Android is a great platform ?", answers = listOf(
                Answer(id = 0, label = "Yes"),
                Answer(id = 1, label = "No")
            )),
            Question(id = 1, correctAnswerId = 1, label = "Android = IOS ?", answers = listOf(
                Answer(id = 0, label = "Yes"),
                Answer(id = 1, label = "No")
            )),
            Question(id = 2, correctAnswerId = 0, label = "Quel est ce cours ?", answers = listOf(
                Answer(id = 0, label = "Mobile"),
                Answer(id = 1, label = "Management")
            )),
            Question(id = 3, correctAnswerId = 2, label = "quelle est la couleur du cheval blanc d'Henry IV ?", answers = listOf(
                Answer(id = 0, label = "Noir"),
                Answer(id = 1, label = "Rouge"),
                Answer(id = 2, label = "Blanc"),
                Answer(id = 3, label = "Arc en ciel")
            ))
        ))
    }
}