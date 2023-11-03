import androidx.compose.desktop.ui.tooling.preview.Preview
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
fun TestPreview() {
    test(title = "Quizz", textValue = "A simple Quizz to discover KMP, KMM and compos");
}

@Preview
@Composable
fun CVPreview() {
    val CvData = CvData();
    CV(CvData);
}