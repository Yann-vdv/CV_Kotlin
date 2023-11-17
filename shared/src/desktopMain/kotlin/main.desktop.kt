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

@Preview
@Composable
fun WelcomePreview() {
    WelcomeScreen();
}

@Preview
@Composable
fun ScorePreview() {
    ScoreScreen("10/20");
}

@Preview
@Composable
fun QuestionPreview() {
    val quizzData = Quizz(
        listOf(
            Question(id = 0, correctId = 0, label = "Android is a great platform ?", answers = listOf(Answer(id = 0, label = "Yes"),Answer(id = 1, label = "No"))),
            Question(id = 1, correctId = 1, label = "Android = IOS ?", answers = listOf(Answer(id = 0, label = "Yes"),Answer(id = 1, label = "No"))),
            Question(id = 2, correctId = 0, label = "Quel est ce cours ?", answers = listOf(Answer(id = 0, label = "Mobile"),Answer(id = 1, label = "Management"))),
            Question(id = 3, correctId = 2, label = "quelle est la couleur du cheval blanc d'Henry IV ?", answers = listOf(Answer(id = 0, label = "Noir"),Answer(id = 1, label = "Rouge"),Answer(id = 2, label = "Blanc"),Answer(id = 3, label = "Arc en ciel")))
        )
    )
    QuestionScreen(quizzData);
}

@Preview
@Composable
fun CVPreview() {
    MaterialTheme {
        CV(CvData());
    }
}