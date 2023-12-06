package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import uk.ac.tees.w9640628.uniclubs.viewmodels.ClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.JoinClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.LoginViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.RegisterViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.UserViewModel

enum class UniClubsScreen() {
    Login,
    HomePage,
    CreateClub,
    ClubUpdates
}

@Composable
fun AppNavigation(){
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController , startDestination = "loginGraph" ){

        navigation(startDestination = "login",route="loginGraph"){
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
        composable("register"){
            RegisterPage(
                viewModel = remember { RegisterViewModel() },
                onRegistrationSuccessful = { message ->
                    navController.navigate("home"){
                        popUpTo("login")
                    }
                }
            )
        }
        }
        composable("home"){
            HomePage(
                viewModel = ClubViewModel(),
                userViewModel = UserViewModel(),
                joinClubViewModel = JoinClubViewModel(),
                navController = navController,
                onJoinClicked = {},

            )
        }
        composable("CreateClub"){
            CreateClub(navController = navController)
        }
        composable("MyClubs"){
            MyClubs(navController = navController)
        }
        composable("clubChat/{clubId}") { backStackEntry ->
            val clubId = backStackEntry.arguments?.getString("clubId")
            val viewModel = rememberChatViewModel(LocalContext.current) // Instantiate your ChatViewModel here if you haven't already
            ChatPage(navController,clubId ?: "", viewModel )
        }

    }

}