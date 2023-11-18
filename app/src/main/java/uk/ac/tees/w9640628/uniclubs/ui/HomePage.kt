package uk.ac.tees.w9640628.uniclubs.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniClubsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    homePage()
                }
            }
        }
    }
}

@Composable
fun homePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        MakeCard(
            imgId = R.drawable.sportsclub ,
            titleId = "Football Club" ,
            descriptionId = "A place for all the football lovers."
        )

        MakeCard(
            imgId = R.drawable.artclub ,
            titleId = "Art Club" ,
            descriptionId = "For people who can redefine the world with art. Join us"
        )

    }
}

@Composable
fun MakeCard(
    imgId: Int,
    titleId: String,
    descriptionId: String,
    modifier: Modifier = Modifier){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .padding(start = 12.dp,end=12.dp,top=8.dp,bottom=8.dp)
    ) {
        Column(modifier = modifier
            .padding(12.dp)
        ){
            Image(
                painter = painterResource(imgId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = titleId,
                fontSize = 24.sp,
                modifier = modifier.padding(4.dp)
            )
            Text(
                text = descriptionId,
                fontSize = 15.sp,
                modifier = modifier.padding(4.dp)
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White),
                modifier = modifier.align(Alignment.End)
            ) {
                Text("Join")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    UniClubsTheme(useDarkTheme = false) {
        homePage()
    }
}