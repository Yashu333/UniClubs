package uk.ac.tees.w9640628.uniclubs.viewmodels

import android.media.MediaRecorder
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AudioRecordViewModel : ViewModel() {

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String? = null

    private val _isRecording = mutableStateOf(false)
    val isRecording: State<Boolean> get() = _isRecording

    init {
        outputFile = Environment.getExternalStorageDirectory().absolutePath + "/audio_record.3gp"
    }

    fun startRecording() {
        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(outputFile)
                prepare()
                start()
            }
            _isRecording.value = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        _isRecording.value = false
    }

    fun getRecordedAudioFilePath(): String? {
        return outputFile
    }
}
