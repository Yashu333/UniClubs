package uk.ac.tees.w9640628.uniclubs.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    private val _imageUrls = mutableStateListOf<String>()
    val imageUrls: List<String> get() = _imageUrls.toList() // Return a copy to avoid unintended modifications

    fun addImageUrl(url: String) {
        _imageUrls.add(url)
    }

    fun setImageUrlsFromFirestore(urls: List<String>) {
        _imageUrls.clear()
        _imageUrls.addAll(urls)
    }
}