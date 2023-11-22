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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier = Modifier, onLoginClicked: (String) -> Unit = {}) {
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
                onLoginClicked(email)
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = Color.White),
            modifier = modifier
                .padding(12.dp)
        ) {
            Text("Login")
        }

    }
}

@Preview
@Composable
fun LoginPreview(){
    UniClubsTheme(useDarkTheme = false) {
        LoginPage()
    }
}

