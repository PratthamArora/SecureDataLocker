package com.pratthamarora.jetpacksecurity.ui.home

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratthamarora.jetpacksecurity.data.FileEntity
import com.pratthamarora.jetpacksecurity.util.EncryptionHelper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.File

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val snackBarMsg: MutableLiveData<String> = MutableLiveData()
    private val context = application
    val fileListEntity: MutableLiveData<ArrayList<FileEntity>> = MutableLiveData()
    private val fileList = ArrayList<FileEntity>()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val bmp: MutableLiveData<Bitmap> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    val fileName: MutableLiveData<String> = MutableLiveData()
    private val dirImage = File(context.filesDir, "images")
    private val dirFile = File(context.filesDir, "documents")


    fun getEncryptedBitmap() {
        viewModelScope.launch {
            val file = File(dirImage, fileName.value!!)
            val encryptedFile = EncryptionHelper.getEncryptedFile(file, context)
            launch(IO) {
                try {
                    encryptedFile.openFileInput().also {
                        val byteArrayInputStream = ByteArrayInputStream(it.readBytes())
                        bmp.postValue(BitmapFactory.decodeStream(byteArrayInputStream))
                    }
                    snackBarMsg.postValue("Image decrypted successfully!")
                } catch (e: Exception) {
                    snackBarMsg.postValue(e.message)
                }
            }
        }
    }

    fun getEncryptedFile() {
        viewModelScope.launch {
            val file = File(dirFile, fileName.value!!)
            val encryptedFile = EncryptionHelper.getEncryptedFile(file, context)
            launch(IO) {
                try {
                    encryptedFile.openFileInput().also {
                        message.postValue(String(it.readBytes(), Charsets.UTF_8))
                    }
                    snackBarMsg.postValue("File decrypted successfully!")
                } catch (e: Exception) {
                    snackBarMsg.postValue(e.message)
                }
            }
        }
    }


    fun getFileList() {
        isLoading.value = true
        viewModelScope.launch(IO) {
            val dir = File(context.filesDir.path)
            val list = dir.listFiles()
            list?.forEach {
                if (it.isDirectory) {
                    fileList.addAll(it.listFiles()?.map { file ->
                        FileEntity(file.name, file, "${file.length() / 1024} Kb")
                    }?.sortedByDescending { fileEntity ->
                        fileEntity.fileName
                    } ?: emptyList())
                    fileListEntity.postValue(fileList)
                }
                if (it.isFile) {
                    fileList.add(FileEntity(it.name, it, "${it.length() / 1024} Kb"))
                    fileListEntity.postValue(fileList)
                }
            }
            isLoading.postValue(false)
        }
    }


}