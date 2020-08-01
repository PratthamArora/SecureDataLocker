package com.pratthamarora.jetpacksecurity.data

import android.content.SharedPreferences

class AppPreference(private val prefs: SharedPreferences) {

    companion object {

        const val KEY_USER_NAME = "PREF_KEY_USER_NAME"
        const val KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL"
        const val KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"

    }
}