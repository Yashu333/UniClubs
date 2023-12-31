package uk.ac.tees.w9640628.uniclubs.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.w9640628.uniclubs.R
import uk.ac.tees.w9640628.uniclubs.viewmodels.ChatViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.ClubViewModel
import uk.ac.tees.w9640628.uniclubs.viewmodels.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPage(
    navController: NavHostController,
    clubId: String,
    viewModel: ChatViewModel = rememberChatViewModel(LocalContext.current)
) {

    var messageText by remember { mutableStateOf("") }
    var userFirstName by remember { mutableStateOf("") }

    //view models
    val clubViewModel = ClubViewModel()
    val userViewModel = UserViewModel()

    val context = LocalContext.current
    var clubName by remember { mutableStateOf("") }

    //Get the current club
    LaunchedEffect(clubId) {
        clubName = clubViewModel.getClubName(clubId, context).toString()
    }

    //getUserFirstName at the start
    LaunchedEffect(Unit) {
        userViewModel.getUserFirstName { firstName ->
            userFirstName = firstName
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$clubName - Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp)
            ) {
                // Chat messages
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(viewModel.getMessagesForClub(clubId)) { message ->
                        // To Display messages
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(
                                    color = if (message.sender == userFirstName) {
                                        // green background for sender message
                                        MaterialTheme.colorScheme.tertiaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.primaryContainer
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            //name
                            Text(
                                text = message.sender,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                            //message
                            Text(
                                text = message.text,
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                // Bottom bar with text input and send button
                BottomBarWithTextField(
                    modifier = Modifier.fillMaxWidth(),
                    messageText = messageText,
                    onMessageTextChanged = { messageText = it },
                    onSendClick = {
                        if (messageText.isNotBlank()) {
                            viewModel.addMessage(messageText, userFirstName, clubId)
                            messageText = ""
                        }
                    },
                    onPhotoClick = {
                                   navController.navigate("camera")
                    }
                )
            }
        }
    )
}

@Composable
fun rememberChatViewModel(context: Context): ChatViewModel {
    return remember {
        ChatViewModel(context)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarWithTextField(
    modifier: Modifier = Modifier,
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onPhotoClick: () -> Unit
) {
    BottomAppBar(
        modifier = modifier
            .padding(4.dp)
            .height(90.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Spacer(modifier = Modifier.width(8.dp))

                // message filed
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { onMessageTextChanged(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            onSendClick()
                        }
                    ),
                    singleLine = true,
                    placeholder = { Text("Type") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                // send button icon
                IconButton(
                    onClick = {
                        onSendClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = stringResource(R.string.send)
                    )
                }

                // Camera icon button
                IconButton(
                    onClick = {
                        onPhotoClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = stringResource(R.string.camera)
                    )
                }
            }
        }
    )
}







