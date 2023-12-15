import data.Answer
import data.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.QuizAPI

class QuizRepository {
    private val quizAPI = QuizAPI()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private var _questionState=  MutableStateFlow(listOf<Question>())
    val questionState = _questionState

    init {
        updateQuiz()
    }

    private suspend fun fetchQuiz(): List<Question> = quizAPI.getAllQuestions().questions

    private fun updateQuiz(){

        coroutineScope.launch {
            val questions = fetchQuiz();
            if (questions.size > 0) {
                _questionState.update { questions }
            } else {
                _questionState.update { listOf(
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
                ) }
            }
        }
    }
}