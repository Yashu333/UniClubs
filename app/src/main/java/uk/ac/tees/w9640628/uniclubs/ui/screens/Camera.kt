package uk.ac.tees.w9640628.uniclubs.ui.screens

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

@Composable
fun Camera(modifier: Modifier = Modifier) {
    var imageCaptured by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }

    // Firebase Storage reference
    val storage = Firebase.storage
    val storageRef: StorageReference = storage.reference

    val captureImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
        if (captured) {
            // Upload the captured image to Firebase Storage
            if (imageCaptured != null) {
                val imageFileName = "image_${System.currentTimeMillis()}.png"
                val imageRef = storageRef.child(imageFileName)
                val uploadTask: UploadTask = imageRef.putFile(imageCaptured!!)

                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Image uploaded successfully, get the download URL
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            val url = uri.toString()
                            println("Image uploaded. Download URL = $url")
                            // Store the URL in the variable
                            imageUrl = url
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

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier) {
        // Example: Trigger the permission request when the button is clicked
        Button(onClick = {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Capture Image")
        }
        AsyncImage(model = imageUrl, contentDescription = null)
    }
}