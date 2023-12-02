package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class UserViewModel : ViewModel() {

    fun getUserEmail(): String? {
        // Get the current authenticated user
        val currentUser = FirebaseAuth.getInstance().currentUser

            // Reload the user's data to get the actual email
        currentUser?.reload()?.addOnCompleteListener {
                // Access the email of the signed-in user
            val userEmail = currentUser?.email
        }

            // Return the current email value (may still be obfuscated)
            return currentUser?.email
    }
}

