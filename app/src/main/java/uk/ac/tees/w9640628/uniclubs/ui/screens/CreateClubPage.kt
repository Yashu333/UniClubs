package uk.ac.tees.w9640628.uniclubs.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClub(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUrl by remember { mutableStateOf<String?>("https://firebasestorage.googleapis.com/v0/b/uniclubs-65916.appspot.com/o/ArtClub.jpg?alt=media&token=86eaf304-7261-4fc5-959d-abe6812c6bed") }
    val dialogOpenState = remember { mutableStateOf(false) }

    val imageUrls = listOf(
        "https://firebasestorage.googleapis.com/v0/b/uniclubs-65916.appspot.com/o/ArtClub.jpg?alt=media&token=86eaf304-7261-4fc5-959d-abe6812c6bed",
        "https://firebasestorage.googleapis.com/v0/b/uniclubs-65916.appspot.com/o/SportsClub.jpg?alt=media&token=504ec2f4-55fc-468e-af2b-7a8ae849196c",
        "https://firebasestorage.googleapis.com/v0/b/uniclubs-65916.appspot.com/o/DanceClub.jpg?alt=media&token=989217f7-f883-4769-af54-2a58eb1dbe34",
        "https://firebasestorage.googleapis.com/v0/b/uniclubs-65916.appspot.com/o/CodingClub.jpg?alt=media&token=802999ef-6fe7-4ce9-9cda-236ce1c444da",
        "https://firebasestorage.googleapis.com/v0/b/uniclubs-65916.appspot.com/o/PhotographyClub.jpg?alt=media&token=2fbdaa4c-9d08-4785-8155-952057d5e489"
        // Add more image URLs as needed
    )

    Surface() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Create a Club",
                fontSize = 32.sp,
                modifier = modifier.padding(top = 24.dp)
            )
            Spacer(modifier = modifier.height(8.dp))
            // Display the selected image or a placeholder
            AsyncImage(
                model = selectedImageUrl,
                contentDescription = null,
                modifier = modifier
                    .height(150.dp).padding(start = 30.dp, end = 30.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = modifier.height(8.dp))
            Button(onClick = { dialogOpenState.value = true }) {
                Text("Select Image")
            }
            Spacer(modifier = modifier.height(28.dp))
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
            )
            Spacer(modifier = modifier.height(12.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                singleLine = true,
            )
            Spacer(modifier = modifier.height(12.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Submit")
            }
            Spacer(modifier = modifier.height(8.dp))
        }
    }

    // Dialog to display the list of images
    if (dialogOpenState.value) {
        Dialog(
            onDismissRequest = { dialogOpenState.value = false },
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(375.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn {
                        items(imageUrls) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clickable {
                                        selectedImageUrl = imageUrl
                                        dialogOpenState.value = false
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

