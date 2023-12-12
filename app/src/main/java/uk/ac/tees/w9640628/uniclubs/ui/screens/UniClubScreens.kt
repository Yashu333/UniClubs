package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import kotlinx.coroutines.delay
import uk.ac.tees.w9640628.uniclubs.UniClubs
import uk.ac.tees.w9640628.uniclubs.viewmodels.CameraViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.ClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.LoginViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.RegisterViewModel

@Composable
fun AppNavigation(){

    val navController: NavHostController = rememberNavController()

    // delay for splash screen using LaunchedEffect
    LaunchedEffect(Unit) {
            delay(2000) // Delay for 2 seconds
            navController.navigate("loginGraph")
    }

    NavHost(navController = navController , startDestination = "splashScreen" ){

        // login graph with login and register pages
        navigation(startDestination = "login",route="loginGraph"){
            composable("login") {
                LoginPage(
                    navController = navController,
                    viewModel = remember { LoginViewModel() },
                    onLoginClicked = { isLoginSuccessful ->
                        if (isLoginSuccessful) {
                            navController.navigate("home"){
                                popUpTo("login")
                            }
                        }
                    }
                )
            }
        composable("register"){
            RegisterPage(
                viewModel = remember { RegisterViewModel() },
                onRegistrationSuccessful = {
                    navController.navigate("home"){
                        popUpTo("login")
                    }
                }
            )
        }
        }

        //home
        composable("home"){
            HomePage(
                viewModel = ClubViewModel(),
                navController = navController,
            )
        }

        //create a club
        composable("CreateClub"){
            CreateClub(navController = navController)
        }

        //my clubs
        composable("MyClubs"){
            MyClubs(navController = navController)
        }

        //For Dynamic chat page navigation
        composable("clubChat/{clubId}") { backStackEntry ->
            val clubId = backStackEntry.arguments?.getString("clubId")
            val viewModel = rememberChatViewModel(LocalContext.current) // Instantiate your ChatViewModel here if you haven't already
            ChatPage(navController,clubId ?: "", viewModel )
        }

        //Camera screen
        composable("camera"){
            val cameraViewModel = CameraViewModel()
            Camera(viewModel = cameraViewModel, navController = navController)
        }

        //Splash Screen
        composable("splashScreen"){
            UniClubs()
        }

        //contact page
        composable("contactUs"){
            ContactUs()
        }

    }

}