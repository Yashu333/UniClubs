package uk.ac.tees.w9640628.uniclubs.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsEndWidth
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.rememberDrawerState
import uk.ac.tees.w9640628.uniclubs.data.Club
import uk.ac.tees.w9640628.uniclubs.data.ClubData
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    clubList: List<Club>,
    navController: NavHostController,
    onJoinClicked: (String) -> Unit = {}
) {
    var drawerState = rememberDrawerState( DrawerValue.Closed)
    var selectedItem by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = modifier.windowInsetsEndWidth(WindowInsets(right = 260.dp)) ,
                windowInsets = WindowInsets.Companion.navigationBars,
                content = {
                    // Your drawer content here
                    Text("Navigation", modifier = Modifier.padding(16.dp))
                    Divider()
                    NavigationDrawerItem(
                        label = { Text(text = "My Clubs") },
                        selected = selectedItem == 0,
                        onClick = {
                            selectedItem = 0
                            // Handle home item click (e.g., navigate to home)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)}
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Create Club") },
                        selected = selectedItem == 1,
                        onClick = {
                            selectedItem = 1
                            // Handle create club item click (e.g., navigate to create club)
                            navController.navigate("CreateClub")
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.Create, contentDescription = null)}
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "All Clubs") },
                        selected = selectedItem == 2,
                        onClick = {
                            selectedItem = 2
                            // Handle all clubs item click (e.g., navigate to all clubs)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {Icon(imageVector = Icons.Default.SelectAll, contentDescription = null)}
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Logout") },
                        selected = selectedItem == 3,
                        onClick = {
                            selectedItem = 3
                            // Handle all clubs item click (e.g., navigate to all clubs)
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
                        title = { Text(text = "UniClubs") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                            }
                        },
                        modifier = Modifier.statusBarsPadding()
                    )
                }
            ){
                // Screen Content
                ClubList(clubList = clubList,modifier.padding(top = 66.dp))
            }
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
    val navController = rememberNavController()

    UniClubsTheme(useDarkTheme = false) {
        HomePage(clubList = ClubData().loadClubs(), navController = navController)
    }
}