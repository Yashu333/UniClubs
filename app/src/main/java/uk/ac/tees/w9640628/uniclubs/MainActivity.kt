package uk.ac.tees.w9640628.uniclubs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.w9640628.uniclubs.ui.screens.AppNavigation
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniClubsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun UniClubs(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginlogo),
            contentDescription = null,
            modifier.size(296.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun UniClubsPreview() {
    UniClubsTheme {
        UniClubs()
    }
}