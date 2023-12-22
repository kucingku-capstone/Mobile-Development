package com.example.kucingku.utils

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "prefs"

    const val UID = "uid"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: ""
    }

    fun putString(key: String, value: String) {
        val prefsEditor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun clear() {
        val prefsEditor = prefs.edit()
        with(prefsEditor) {
            clear()
            commit()
        }
    }
}
