package uk.ac.tees.w9640628.uniclubs.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import uk.ac.tees.w9640628.uniclubs.data.Club
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme
import uk.ac.tees.w9640628.uniclubs.viewmodels.ClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyClubs(modifier: Modifier = Modifier) {
    val clubViewModel = viewModel<ClubViewModel>()
    val userViewModel = viewModel<UserViewModel>()
    var joinedClubList by remember { mutableStateOf<List<String>>(emptyList()) }

    // Collect the user's joined clubs using the UserViewModel
    LaunchedEffect(true) {
        userViewModel.getUserJoinedClubs { clubs ->
            joinedClubList = clubs
        }
    }

    // Collect the club list StateFlow from the ClubViewModel
    val clubList by clubViewModel.clubList.collectAsState()

    // Filter the clubs based on the joined clubs
    val filteredClubs = clubList.filter { club ->
        club.id in joinedClubList
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Clubs") },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ){
        if (filteredClubs.isEmpty()){
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please join a club and come back here.",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
        else{
            ClubList1(clubList = filteredClubs,modifier.padding(top = 66.dp))
        }
    }

}


@Composable
fun ClubList1 (clubList: List<Club>, modifier: Modifier = Modifier) {

    val navController: NavHostController = rememberNavController()
    LazyColumn(modifier = modifier) {
        items(clubList) { club ->
            MakeCard1(
                club = club,
                modifier = Modifier.padding(0.dp).clickable {
                    // Navigate to the chat page for the selected club
                    navController.navigate("chat/${club.id}")
                }
            )
        }
    }
}


@Composable
fun MakeCard1(
    club: Club,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Column(
            modifier = modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = club.image,
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
        }
    }
}

@Preview
@Composable
fun MyClubsPreview(){
    UniClubsTheme() {
        MyClubs()
    }
}
