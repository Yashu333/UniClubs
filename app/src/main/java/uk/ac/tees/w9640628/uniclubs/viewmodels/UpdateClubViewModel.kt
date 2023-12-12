package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class UpdateClubViewModel : ViewModel() {
    private val clubsCollection = FirebaseFirestore.getInstance().collection("Clubs")

    // converting to firestore clubs format to add a new club
    fun updateClub(imageUrl: String, title: String, description: String, id:Int) {
        val club = hashMapOf(
            "Name" to title,
            "Description" to description,
            "Image" to imageUrl,
            "Id" to id
        )

        clubsCollection.add(club)

    }

    companion object {
        private const val TAG = "UpdateClubViewModel"
    }
}
