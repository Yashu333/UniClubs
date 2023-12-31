package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme
import uk.ac.tees.w9640628.uniclubs.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    loginText: String = "Login",
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(),
    onLoginClicked: (Boolean) -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .verticalScroll(rememberScrollState())
    ) {
        // Display error message in Snackbar
        errorMessage?.let {
            Snackbar(
                containerColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(32.dp),
                action = {
                    TextButton(onClick = { errorMessage = null }) {
                        Text("Dismiss", color = Color.White)
                    }
                },
            ) {
                Text(it, color = Color.White)
            }
        }
        //Logo
        Image(
            painter = painterResource(id = R.drawable.loginlogo),
            contentDescription = null,
            modifier.size(164.dp)
        )
        Spacer(modifier = modifier.height(40.dp))

        Text(
            text = "Login",
            fontSize = 35.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = modifier.height(8.dp))

        //email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Mail,
                    contentDescription = stringResource(R.string.email)
                )
            },
            modifier = modifier.horizontalScroll(rememberScrollState())
        )

        //Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = stringResource(R.string.password)
                )
            },
        )

        //Snack bar messages
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Missing fields. Please fill in all required fields."
                } else if (!isValidEmail(email)) {
                    errorMessage = "Invalid email format. Please enter a valid email."
                } else {
                    viewModel.loginUser(email, password) { isLoginSuccessful ->
                        if (isLoginSuccessful) {
                            onLoginClicked(true)
                        } else {
                            errorMessage = "Invalid credentials. Please try again."
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = Color.White
            ),
            modifier = modifier.padding(12.dp)
        ) {
            Text(loginText)
        }

        Spacer(modifier = modifier.height(8.dp))

        // Text button to navigate to register page
        TextButton(
            onClick = {
                navController.navigate("register")
            }
        ) {
            Text(
                stringResource(R.string.register_here),
                fontSize = 16.sp
            )
        }
    }
}

// validate email format
private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


@Preview
@Composable
fun LoginPagePreview() {
    val navController = rememberNavController()

    UniClubsTheme(useDarkTheme = false) {
        LoginPage(
            navController = navController,
            viewModel = LoginViewModel()
        )
    }
}

