package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.data.Club
import uk.ac.tees.w9640628.uniclubs.data.ClubData
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme

@Composable
fun HomePage(modifier: Modifier = Modifier,clubList : List<Club>,onJoinClicked: (String) -> Unit = {}) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        ClubList(clubList = ClubData().loadClubs())

    }
}

@Composable
fun ClubList(clubList: List<Club>, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier) {
        items(clubList) { club ->
            MakeCard(
                club = club,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Composable
fun MakeCard(
    club: Club,
    onJoinClicked: (String) -> Unit = {},
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
                painter = painterResource(club.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = club.name,
                fontSize = 24.sp,
                modifier = modifier.padding(4.dp)
            )
            Text(
                text = club.description,
                fontSize = 15.sp,
                modifier = modifier.padding(4.dp)
            )
            Button(
                onClick = {onJoinClicked},
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
        HomePage(clubList = ClubData().loadClubs())
    }
}