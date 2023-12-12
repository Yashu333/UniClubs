package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {

    val currentUser = FirebaseAuth.getInstance().currentUser
    fun getUserEmail(): String? {
        // Reloading the data to get email
        currentUser?.reload()?.addOnCompleteListener {
        }

        // Return the current email value
        return currentUser?.email
    }

    fun getUserJoinedClubs(callback: (List<String>) -> Unit) {
        val usersCollection = FirebaseFirestore.getInstance().collection("users")
        var currentJoinClubs: List<String> = emptyList()

        // document where the 'email' field matches 'userEmail'
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
                callback(emptyList())
            }
    }

    fun getUserFirstName(callback: (String) -> Unit) {
        val usersCollection = FirebaseFirestore.getInstance().collection("users")

        // Check if currentUser and currentUser.email are not null
        val userEmail = currentUser?.email
        if (userEmail != null) {
            // document where the 'email' field matches 'userEmail'
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
                }
        }
    }


}
