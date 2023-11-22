package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class UniClubsScreen() {
    Login,
    HomePage,
    CreateClub,
    ClubUpdates
}

@Composable
fun AppNavigation(){
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController , startDestination = "login" ){
        composable("login"){
            LoginPage(
                onLoginClicked = {
                    email ->
                    navController.navigate("home")
                }
            )
        }
        composable("home"){
            HomePage(
                onJoinClicked = {navController.navigate("CreateClub")}
            )
        }
        composable("CreateClub"){
            CreateClub()
        }
    }

}