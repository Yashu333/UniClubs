package uk.ac.tees.w9640628.uniclubs.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import uk.ac.tees.w9640628.uniclubs.data.Club

class ClubViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _clubList = MutableStateFlow<List<Club>>(emptyList())
    val clubList: StateFlow<List<Club>> get() = _clubList

    init {
        // fetch clubs from Firestore
        fetchClubs()
    }

    private fun fetchClubs() {

        val clubsCollection = firestore.collection("Clubs")

        // Observe changes in the collection and update the StateFlow
        clubsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            // Parse the snapshot to a list of Club objects
            val clubs = snapshot?.documents?.map { document ->
                val id = document.id
                val name = document["Name"] as String
                val image = document["Image"] as String
                val description = document["Description"] as String

                Club(id, name, description, image)
            } ?: emptyList()

            // Update the StateFlow
            _clubList.value = clubs
        }
    }

    suspend fun getClubName(clubId: String,context: Context): String? {
        val clubsCollection = Firebase.firestore.collection("Clubs")

        // Use await() to make the Firestore call synchronous within a coroutine
        return try {
            val documentSnapshot = clubsCollection.document(clubId).get().await()

            // Check if the document exists
            if (documentSnapshot.exists()) {
                // Get the value of the 'club.name' field
                documentSnapshot.getString("Name")
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
