package com.pratthamarora.jetpacksecurity.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.io.File

object EncryptionHelper {
    private const val prefName = "com.pratthamarora.jetpacksecurity.prefs"
    private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    fun getSharedPrefs(context: Context): SharedPreferences {
        val keyEncryptedScheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
        val valueEncryptedScheme = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

        return EncryptedSharedPreferences.create(
            prefName,
            masterKey,
            context,
            keyEncryptedScheme,
            valueEncryptedScheme
        )
    }

    fun getEncryptedFile(file: File, context: Context): EncryptedFile {
        val fileEncryptionScheme = EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        return EncryptedFile.Builder(
            file,
            context,
            masterKey,
            fileEncryptionScheme
        ).build()
    }
}