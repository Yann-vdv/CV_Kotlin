import data.Answer
import data.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.CacheQuizAPI
import network.QuizAPI

class QuizRepository {
    private val quizAPI = QuizAPI()
    private val cacheQuizAPI = CacheQuizAPI()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var _questionState=  MutableStateFlow(listOf<Question>())
    val questionState = _questionState

    init {
        updateQuiz()
    }

    private suspend fun fetchQuiz(): List<Question> = quizAPI.getAllQuestions().questions

    private suspend fun fetchCacheQuiz(): List<Question> = cacheQuizAPI.getAllQuestions().questions

    private fun updateQuiz(){

        coroutineScope.launch {
            try {
                _questionState.update { fetchQuiz() }
            } catch (err:Exception) {
                //println(err)
                _questionState.update { fetchCacheQuiz() }
            }
        }
    }
}