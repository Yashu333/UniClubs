package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    fun loginUser(email: String, password: String, onLoginResult: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onLoginResult(task.isSuccessful)
            }
    }
}
