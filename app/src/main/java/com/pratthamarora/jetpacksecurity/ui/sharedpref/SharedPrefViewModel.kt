package com.pratthamarora.jetpacksecurity.ui.sharedpref

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pratthamarora.jetpacksecurity.data.AppPreference
import com.pratthamarora.jetpacksecurity.data.UserRepository

class SharedPrefViewModel(application: Application) : AndroidViewModel(application) {
    private var sharedPrefs = application.getSharedPreferences(
        "com.pratthamarora.jetpacksecurity.prefs",
        Context.MODE_PRIVATE
    )
    private var appPreference = AppPreference(sharedPrefs)
    private var userRepository = UserRepository(appPreference)
    val userName: MutableLiveData<String> = MutableLiveData()
    val userEmail: MutableLiveData<String> = MutableLiveData()
    val snackBarMsg: MutableLiveData<String> = MutableLiveData()

    fun saveUserData() {
        userRepository.saveUserData(userName.value!!, userEmail.value!!)
        getUserName()
        getUserEmail()
        snackBarMsg.value = "Credentials saved successfully!"
    }

    fun getUserName() {
        userName.value = userRepository.getUserName()
    }

    fun getUserEmail() {
        userEmail.value = userRepository.getUserEmail()
    }

}