package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class JoinClubViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    fun joinClub(userEmail: String, clubName: String) {
        // Reference to the 'users' collection
        val usersCollection = firestore.collection("users")

        // Query for the document where the 'email' field matches 'userEmail'
        usersCollection.whereEqualTo("email", userEmail).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    // Update the 'joinedClubs' field in the user's document or create it if not present
                    val currentJoinClubs = document.get("joinedClubs") as? List<String> ?: emptyList()
                    val updatedJoinClubs = (currentJoinClubs + clubName).toSet()

                    val data = hashMapOf("joinedClubs" to updatedJoinClubs.toList())
                    usersCollection.document(document.id).set(data, SetOptions.merge())
                        .addOnSuccessListener {
                            // Club joined successfully
                        }
                        .addOnFailureListener { e ->
                            // Handle failure
                        }
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }
}


