package com.pratthamarora.jetpacksecurity.ui.image

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratthamarora.jetpacksecurity.util.EncryptionHelper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    val snackBarMsg: MutableLiveData<String> = MutableLiveData()
    private val context = application
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val dir = File(context.filesDir, "images")
    val bitmap: MutableLiveData<Bitmap> = MutableLiveData()

    fun saveEncryptedBitmap() {
        viewModelScope.launch {
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap.value!!, 1080, 780, true)
            val date = Date()
            val fileName = "EncryptedImage ${dateFormat.format(date)}.jpg"
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, fileName)

            launch(IO) {
                try {
                    val encryptedFile = EncryptionHelper.getEncryptedFile(file, context)
                    val byteArray = ByteArrayOutputStream()
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                    encryptedFile.openFileOutput().also {
                        it.write(byteArray.toByteArray())
                        it.flush()
                        it.close()
                    }

                    snackBarMsg.postValue("File saved successfully!")
                } catch (e: Exception) {
                    snackBarMsg.postValue(e.message)
                }
            }
        }
    }


}