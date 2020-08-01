package com.pratthamarora.jetpacksecurity.data

class UserRepository(private val appPreference: AppPreference) {

    fun saveUserData(name: String, email: String) {
        appPreference.apply {
            setUserName(name)
            setUserEmail(email)
        }
    }

    fun getUserName() = appPreference.getUserName()
    fun getUserEmail() = appPreference.getUserEmail()

    fun setMasterKey(key: String) = appPreference.setMasterKey(key)
    fun getMasterKey() = appPreference.getMasterKey()

}