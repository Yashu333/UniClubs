package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.ac.tees.w9640628.uniclubs.data.Club

class ClubViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _clubList = MutableStateFlow<List<Club>>(emptyList())
    val clubList: StateFlow<List<Club>> get() = _clubList

    init {
        // Call a function to fetch clubs from Firestore when the ViewModel is created
        fetchClubs()
    }

    private fun fetchClubs() {
        // Reference to the "Clubs" collection
        val clubsCollection = firestore.collection("Clubs")

        // Observe changes in the collection and update the StateFlow
        clubsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Handle error
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

            // Update the StateFlow with the new data
            _clubList.value = clubs
        }
    }
}
