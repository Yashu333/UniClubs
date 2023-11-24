package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun registerUser(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                // Create user in Firebase Authentication
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()

                // If successful, store user data in Firestore
                val user = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email
                )

                firestore.collection("users")
                    .document(authResult.user?.uid ?: "")
                    .set(user)
                    .await()
            } catch (e: Exception) {
                // Handle registration failure
            }
        }
    }
}

