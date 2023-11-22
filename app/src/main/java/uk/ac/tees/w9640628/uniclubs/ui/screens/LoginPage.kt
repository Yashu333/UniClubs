package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.w9640628.uniclubs.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavHostController, viewModel: LoginViewModel = viewModel(), onLoginClicked: (Boolean) -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ){
        Text(
            text = "Login",
            fontSize = 35.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it} ,
            label = { Text("Email") },
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it} ,
            label = { Text("Password") }
        )
        Button(
            onClick = {
                viewModel.loginUser(email, password) { isLoginSuccessful ->
                        onLoginClicked(isLoginSuccessful)
                    }
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = Color.White),
            modifier = modifier
                .padding(12.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = modifier.height(8.dp))
        TextButton(
            onClick = {
                navController.navigate("register")
            }
        ) {
            Text(
                "Not a user, Register here",
                fontSize = 16.sp
            )
        }

    }
}

@Preview
@Composable
fun LoginPagePreview() {
    // Initialize a NavController for preview
    val navController = rememberNavController()

    // Use the LoginPage composable for preview
    UniClubsTheme(useDarkTheme = false) {
        LoginPage(
            navController = navController,
            viewModel = LoginViewModel()
        )
    }
}

