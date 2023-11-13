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
import uk.ac.tees.w9640628.uniclubs.ui.ui.theme.UniClubsTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.w9640628.uniclubs.R

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
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun homePage(modifier: Modifier = Modifier) {
    Surface(modifier =  modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Card(modifier = modifier) {
            Column(modifier = modifier.padding(12.dp)){
                Image(
                    painter = painterResource(R.drawable.sportsclub),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(194.dp),
                    contentScale = ContentScale.Crop
                )
                Text("Sports Club",fontSize = 24.sp)
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    UniClubsTheme {
        homePage()
    }
}