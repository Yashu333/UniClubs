package uk.ac.tees.w9640628.uniclubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.ui.theme.UniClubsTheme

@Composable
fun ContactUs(modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = stringResource(R.string.queries),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.height(16.dp))

        //Use the details function for all details
        Details(icon = Icons.Filled.Person,text = stringResource(R.string.fname))
        Details(icon = Icons.Filled.Email,text = stringResource(R.string.uni_email))
        Details(icon = Icons.Filled.Phone,text = stringResource(R.string.phone_number))
    }
}

@Composable
fun Details(text: String, icon: ImageVector, modifier: Modifier = Modifier) {

        // For Text with a leading icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = modifier.padding(end = 6.dp)
            )
            Text(
                text = text,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = modifier.height(6.dp))

}


@Preview
@Composable
fun ContactUsPreview(){
    UniClubsTheme() {
        ContactUs()
    }
}