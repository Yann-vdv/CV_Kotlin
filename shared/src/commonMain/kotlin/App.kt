import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
//    MaterialTheme {
//        var greetingText by remember { mutableStateOf("Hello, World!") }
//        var showImage by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = {
//                greetingText = "Hello, ${getPlatformName()}"
//                showImage = !showImage
//            }) {
//                Text(greetingText)
//            }
//            AnimatedVisibility(showImage) {
//                Image(
//                    painterResource("compose-multiplatform.xml"),
//                    contentDescription = "Compose Multiplatform icon"
//                )
//            }
//        }
        //test(title = "Quizz", textValue = "A simple Quizz to discover KMP, KMM and compose");
//    }
    val CvData = CvData();
    CV(CvData);
}

@Composable
@OptIn(ExperimentalResourceApi::class)
internal fun CV(data:CvData) {
    MaterialTheme {
        Row(modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column(modifier = Modifier.width(250.dp)) {
//                        AsyncImage(
//                            model = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
//                            contentDescription = "Photo de profil"
//                        )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(250.dp)
                ) {
                    Image(
                        painterResource("compose-multiplatform.xml"),
                        contentDescription = "Compose Multiplatform icon",
                        modifier = Modifier.size(140.dp)
                    )
                    Text(
                        data.lastName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(data.firstName, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Text(data.age.toString() + " ans", fontWeight = FontWeight.SemiBold)
                    Text(data.subject, fontWeight = FontWeight.SemiBold)
                }
                Text("PROFIL", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(data.profilDesc)
                Text("LANGUES", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                data.languages.forEach { langue ->
                    Language(
                        title = langue.name,
                        score = langue.score
                    )
                }
                Text("CONTACT", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("TELEPHONE :", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(data.phone)
                Text("E-MAIL :", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(data.email)
            }
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Column(modifier = Modifier.padding(end = 20.dp)) {
                Text(
                    "EXPERIENCES PROFESSIONNELLES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                data.paragraph.forEach { para -> Par(para) }
                Text("FORMATIONS", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                data.paragraph2.forEach { para -> Par(para) }
            }
        }
    }
}

@Composable
internal fun Par(data: ParData) {
    Spacer(modifier = Modifier.padding(top = 10.dp))
    Column {
        Text(text = data.title, fontWeight = FontWeight.Bold)
        Text(data.date)
        Text(data.description)
    }
}

@Composable
internal fun Language(title: String, score: Int) {
    Row {
        Text(text = title, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.padding(end = 5.dp))
        if (score == 1) Text(text = "X")
        else if (score==2) Text(text = "XX")
        else if (score==3) Text(text = "XXX")
        else if (score==4) Text(text = "XXXX")
        else if (score==5) Text(text = "XXXXX")
    }
}

@Composable
internal fun test(title:String,textValue:String) {
    Card() {
        Column() {
            Text(text = title)
            Text(text = textValue)
        }
    }
}

//@Composable
//internal fun test(title:String,textValue:String) {
//    Card() {
//        Column() {
//            Text(text = title)
//            Text(text = textValue)
//            Button(onClick = {}) {
//                Text(text = "Start Quizz")
//            }
//        }
//    }
//}

expect fun getPlatformName(): String