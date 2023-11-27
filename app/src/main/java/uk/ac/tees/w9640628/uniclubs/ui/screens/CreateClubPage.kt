package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClub(modifier: Modifier = Modifier) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Surface() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
        ) {
            Text(
                text = "Create a Club",
                fontSize = 32.sp,
                modifier = modifier.padding(top = 24.dp)
            )
            Spacer(modifier = modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.sportsclub ) ,
                contentDescription = null,
                modifier = modifier
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = modifier.height(8.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Upload File")
            }
            Spacer(modifier = modifier.height(28.dp))
            TextField(
                value = title ,
                onValueChange = {title = it},
                label = { Text("Title") },
                singleLine = true,
            )
            Spacer(modifier = modifier.height(12.dp))
            TextField(
                value = description ,
                onValueChange = {description = it},
                label = { Text("Description") },
                singleLine = true,
            )
            Spacer(modifier = modifier.height(12.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Submit")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    UniClubsTheme {
        CreateClub()
    }
}