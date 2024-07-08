package com.example.prodiggy_ad_02.data

import android.content.Context
import android.content.SharedPreferences

object SharedUtility {

    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_USER_ID = "userId"

    fun saveUserId(context: Context, userId: Int) {
        val sharedPrefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putInt(KEY_USER_ID, userId)
            apply()
        }
    }

    fun getUserId(context: Context): Int {
        val sharedPrefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getInt(KEY_USER_ID, -1) // -1 is the default value if key is not found
    }

    fun clearUserId(context: Context) {
        val sharedPrefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            remove(KEY_USER_ID)
            apply()
        }
    }
}
