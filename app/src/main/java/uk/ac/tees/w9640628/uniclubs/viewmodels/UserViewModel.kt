package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {

    val currentUser = FirebaseAuth.getInstance().currentUser
    fun getUserEmail(): String? {
        // Reload the user's data to get the actual email
        currentUser?.reload()?.addOnCompleteListener {
            // Access the email of the signed-in user
            val userEmail = currentUser?.email
        }

        // Return the current email value (may still be obfuscated)
        return currentUser?.email
    }

    fun getUserJoinedClubs(callback: (List<String>) -> Unit) {
        val usersCollection = FirebaseFirestore.getInstance().collection("users")
        var currentJoinClubs: List<String> = emptyList()

        // Query for the document where the 'email' field matches 'userEmail'
        usersCollection.whereEqualTo("email", currentUser?.email).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    // Update the 'joinedClubs' field in the user's document or create it if not present
                    currentJoinClubs = document.get("joinedClubs") as? List<String>?: emptyList()
                    break
                }
                callback(currentJoinClubs)
            }
            .addOnFailureListener { e ->
                // Handle failure
                callback(emptyList())
            }
    }

    fun getUserFirstName(callback: (String) -> Unit) {
        val usersCollection = FirebaseFirestore.getInstance().collection("users")

        // Check if currentUser and currentUser.email are not null before proceeding
        val userEmail = currentUser?.email
        if (userEmail != null) {
            // Query for the document where the 'email' field matches 'userEmail'
            usersCollection.whereEqualTo("email", userEmail).get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        // Update the 'joinedClubs' field in the user's document or create it if not present
                        val userFirstName = document.getString("firstName")
                        if (userFirstName != null) {
                            callback(userFirstName)
                            return@addOnSuccessListener
                        }
                    }
                    callback("") // or any other appropriate default value
                }
                .addOnFailureListener { e ->
                    callback("") // or any other appropriate default value
                }
        } else {
            callback("") // or any other appropriate default value
        }
    }


}
