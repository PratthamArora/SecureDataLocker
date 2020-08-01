package com.pratthamarora.jetpacksecurity.data

import android.content.SharedPreferences

class AppPreference(private val prefs: SharedPreferences) {

    companion object {

        const val KEY_USER_NAME = "PREF_KEY_USER_NAME"
        const val KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL"
        const val KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"

    }

    fun setUserName(name: String) = prefs.edit().putString(KEY_USER_NAME, name).apply()

    fun getUserName(): String? = prefs.getString(KEY_USER_NAME, "")

    fun setUserEmail(Email: String) = prefs.edit().putString(KEY_USER_EMAIL, Email).apply()

    fun getUserEmail(): String? = prefs.getString(KEY_USER_EMAIL, "")

    fun setMasterKey(masterKey: String) =
        prefs.edit().putString(KEY_ACCESS_TOKEN, masterKey).apply()

    fun getMasterKey(): String? = prefs.getString(KEY_ACCESS_TOKEN, "")

}