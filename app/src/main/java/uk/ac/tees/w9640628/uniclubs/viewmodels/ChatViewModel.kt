package uk.ac.tees.w9640628.uniclubs.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Message(
    val text: String,
    val sender: String,
    val timestamp: Long,
    val clubId: String
)

class ChatViewModel(context: Context) : ViewModel() {

    private val sharedPreferences =
        context.getSharedPreferences("ChatMessages", Context.MODE_PRIVATE)

    private val _messages = mutableStateListOf<Message>()

    init {
        // Load messages from SharedPreferences during ViewModel initialization
        loadMessages()
    }

    private fun loadMessages() {
        val savedMessages = sharedPreferences.getString("messages", null)
        _messages.clear()
        savedMessages?.split(";")?.forEach { messageString ->
            val parts = messageString.split(",")
            if (parts.size == 4) {
                val message = Message(parts[0], parts[1], parts[2].toLong(), parts[3])
                _messages.add(message)
            }
        }
    }

    private fun saveMessages() {
        val messagesString = _messages.joinToString(";") { message ->
            "${message.text},${message.sender},${message.timestamp},${message.clubId}"
        }
        sharedPreferences.edit().putString("messages", messagesString).apply()
    }

    fun addMessage(text: String, sender: String, clubId: String) {
        val message = Message(text, sender, System.currentTimeMillis(), clubId)
        _messages.add(message)
        saveMessages()
    }

    fun getMessagesForClub(clubId: String): List<Message> {
        return _messages.filter { it.clubId == clubId }
    }
}


