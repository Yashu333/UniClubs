package uk.ac.tees.w9640628.uniclubs.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import uk.ac.tees.w9640628.uniclubs.viewmodels.CameraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Camera(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel,
    navController: NavHostController
) {
    var imageCaptured by remember { mutableStateOf<Uri?>(null) }
    var imagesFromFirebase by remember { mutableStateOf<List<String>>(emptyList()) }

    // Firebase Storage reference
    val storage = Firebase.storage
    val storageRef: StorageReference = storage.reference

    // Firestore reference
    val firestore = Firebase.firestore
    val chatImagesCollectionRef = firestore.collection("chatImages")
    val chatDocumentId = "M7S13R98PVAMmilv4ID5"

    // Fetch image URLs from Firestore when the composable is first composed
    LaunchedEffect(chatDocumentId) {
        while (true) {
            val documentSnapshot = chatImagesCollectionRef.document(chatDocumentId).get().await()
            val urls = documentSnapshot["images"] as? List<String> ?: emptyList()
            imagesFromFirebase = urls

            // Optional: If you want to update the view model with fetched URLs
            viewModel.setImageUrlsFromFirestore(urls)

            // Delay to avoid excessive queries
            delay(2000)
        }
    }


    val captureImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
        if (captured) {
            // Upload the captured image to Firebase Storage
            imageCaptured?.let { capturedImage ->
                val imageFileName = "image_${System.currentTimeMillis()}.png"
                val imageRef = storageRef.child(imageFileName)
                val uploadTask: UploadTask = imageRef.putFile(capturedImage)

                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Image uploaded successfully, get the download URL
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            val url = uri.toString()
                            println("Image uploaded. Download URL = $url")

                            // Update Firestore with the new URL
                            chatImagesCollectionRef
                                .document(chatDocumentId)
                                .update("images", FieldValue.arrayUnion(url))
                        }
                    } else {
                        // Handle upload failure
                        println("Image upload failed.")
                    }
                }
            }
        } else {
            // Handle capture failure or cancellation
            println("Image capture canceled or failed.")
        }
    }

    val selectFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("image/*")) { imageUri ->
            if (imageUri != null) {
                // Process the selected file
                imageCaptured = imageUri
                captureImageLauncher.launch(imageUri)
            } else {
                // Handle file selection failure or cancellation
                println("File selection failed or canceled.")
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // Launch the file selection when permission is granted
                selectFileLauncher.launch("image_${System.currentTimeMillis()}.png")
            } else {
                // Handle permission denial
                println("Permission denied.")
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Images") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.outline)
            ) {
                // Display captured images from Firebase Storage
                LazyColumn(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, bottom= 8.dp, top = 68.dp)
                ) {
                    items(imagesFromFirebase) { imageUrl ->
                        // Use Coil or any other image loading library to load and display images
                        // For simplicity, AsyncImage is used here, but you might need to replace it
                        LazyRow {
                            item {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(200.dp)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                // Example: Trigger the permission request when the button is clicked
                Button(onClick = {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                },colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                )) {
                    Text("Capture Image")
                }
            }
        }
    )

}
