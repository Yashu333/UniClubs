package uk.ac.tees.w9640628.uniclubs.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.data.Club
import uk.ac.tees.w9640628.uniclubs.viewmodels.ClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.JoinClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: ClubViewModel,
    navController: NavHostController
) {
    // Nav bar state
    val drawerState = rememberDrawerState( DrawerValue.Closed)
    var selectedItem by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // Clubs from Firestore
    val clubList by viewModel.clubList. collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = modifier.windowInsetsEndWidth(WindowInsets(right = 260.dp)) ,
                windowInsets = WindowInsets.Companion.navigationBars,
                content = {

                    Text("Navigation", modifier = Modifier.padding(16.dp))
                    Divider()
                    // All clubs
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.all_clubs)) },
                        selected = selectedItem == 0,
                        onClick = {
                            selectedItem = 0
                            // navigate to home
                            navController.navigate("home")
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)}
                    )
                    //Create Club
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.create_club)) },
                        selected = selectedItem == 1,
                        onClick = {
                            selectedItem = 1
                            // navigate to create club
                            navController.navigate("CreateClub")
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.Create, contentDescription = null)}
                    )
                    //My Clubs
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.my_clubs)) },
                        selected = selectedItem == 2,
                        onClick = {
                            selectedItem = 2
                            // navigate to my clubs
                            navController.navigate("MyClubs")
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.SelectAll, contentDescription = null)}
                    )
                    //Contact Us
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.contact_us)) },
                        selected = selectedItem == 3,
                        onClick = {
                            selectedItem = 3
                            // navigate to contact us page
                            navController.navigate("contactUs")
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.ContactPage, contentDescription = null)}
                    )
                    //Logout
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.logout)) },
                        selected = selectedItem == 4,
                        onClick = {
                            selectedItem = 4
                            navController.navigate("login"){
                                popUpTo("login")
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.Logout, contentDescription = null)}
                    )
                }
                )
        },
    ){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(R.string.app_title)) },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(R.string.menu))
                            }
                        },
                        modifier = Modifier.statusBarsPadding()
                    )
                }
            ){
                // Screen Content
                ClubList(clubList = clubList, modifier.padding(top = 66.dp))
    }
}}


@Composable
fun ClubList(clubList: List<Club>, modifier: Modifier = Modifier) {

    val joinClubViewModel = JoinClubViewModel()
    val userViewModel = UserViewModel()

    LazyColumn(modifier = modifier) {
        items(clubList) { club ->
            MakeCard(
                club = club,
                joinClubViewModel = joinClubViewModel,
                userViewModel = userViewModel,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}


@Composable
fun MakeCard(
    club: Club,
    userViewModel: UserViewModel,
    joinClubViewModel: JoinClubViewModel,
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
            //To display images from internet
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
            var hasUserJoined by remember { mutableStateOf(false) }

            // Fetch the user's joined clubs
            userViewModel.getUserJoinedClubs { userJoinedClubs ->
                if (userJoinedClubs.contains(club.id)) {
                    hasUserJoined = true
                }
            }

            //Join and Joined button
            Button(
                onClick = {
                    val email = userViewModel.getUserEmail()
                    if (email != null) {
                        joinClubViewModel.joinClub(email, club.id)
                        hasUserJoined = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasUserJoined) Color.Gray else MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                ),
                modifier = modifier.align(Alignment.End)
            ) {
                Text(if (hasUserJoined) "Joined" else "Join")
            }

        }
    }
}
