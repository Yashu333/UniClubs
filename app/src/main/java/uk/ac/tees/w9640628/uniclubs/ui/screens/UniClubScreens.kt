package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import uk.ac.tees.w9640628.uniclubs.data.ClubData
import uk.ac.tees.w9640628.uniclubs.viewmodels.LoginViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.RegisterViewModel

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
            composable("register"){
                RegisterPage(
                    viewModel = remember { RegisterViewModel() },
                    onRegistrationSuccessful = { message ->
                            navController.navigate("login")
                    }
                )
            }
            composable("login") {
                LoginPage(
                    navController = navController,
                    viewModel = remember { LoginViewModel() },
                    onLoginClicked = { isLoginSuccessful ->
                        if (isLoginSuccessful) {
                            navController.navigate("home")
                        }
                    }
                )
            }
        composable("home"){
            HomePage(
                clubList = ClubData().loadClubs(),
                onJoinClicked = {navController.navigate("CreateClub")}
            )
        }
        composable("CreateClub"){
            CreateClub()
        }
    }

}