package com.pratthamarora.jetpacksecurity.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratthamarora.jetpacksecurity.data.FileEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val snackBarMsg: MutableLiveData<String> = MutableLiveData()
    private val context = application
    val fileListEntity: MutableLiveData<ArrayList<FileEntity>> = MutableLiveData()
    private val fileList = ArrayList<FileEntity>()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

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