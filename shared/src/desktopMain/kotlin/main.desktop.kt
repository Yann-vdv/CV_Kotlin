import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() = App()

@Preview
@Composable
fun AppPreview() {
    App()
}

//@Preview
//@Composable
//fun QuestionPreview() {
//    val quizzData = Quiz(
//        listOf(
//            Question(id = 0, correctAnswerId = 0, label = "Android is a great platform ?", answers = listOf(Answer(id = 0, label = "Yes"),Answer(id = 1, label = "No"))),
//            Question(id = 1, correctAnswerId = 1, label = "Android = IOS ?", answers = listOf(Answer(id = 0, label = "Yes"),Answer(id = 1, label = "No"))),
//            Question(id = 2, correctAnswerId = 0, label = "Quel est ce cours ?", answers = listOf(Answer(id = 0, label = "Mobile"),Answer(id = 1, label = "Management"))),
//            Question(id = 3, correctAnswerId = 2, label = "quelle est la couleur du cheval blanc d'Henry IV ?", answers = listOf(Answer(id = 0, label = "Noir"),Answer(id = 1, label = "Rouge"),Answer(id = 2, label = "Blanc"),Answer(id = 3, label = "Arc en ciel")))
//        )
//    )
//    QuestionScreen(quizzData.questions);
//}