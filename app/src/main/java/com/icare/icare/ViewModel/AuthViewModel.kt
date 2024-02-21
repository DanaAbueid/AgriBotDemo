package com.icare.icare.ViewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel


// Initialize SharedPreferences in your ViewModel
class AuthViewModel : ViewModel() {
    var accessToken: String? = null
    var refreshToken: String? = null
    private val sharedPreferencesKey = "MyPrefs"
    private val userIdKey = "userId"
    private lateinit var sharedPrefs: SharedPreferences
    var user_id: Long = -1

    fun initSharedPreferences(context: Context) {
        sharedPrefs = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        user_id = sharedPrefs.getLong(userIdKey, -1)
    }

    fun setUserId(context: Context, userId: Long) {
        sharedPrefs = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        this.user_id = userId
        sharedPrefs.edit().putLong(userIdKey, userId).apply()
    }
}
