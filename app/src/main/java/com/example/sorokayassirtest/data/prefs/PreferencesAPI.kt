package com.example.sorokayassirtest.data.prefs

import android.content.Context
import javax.inject.Inject

class PreferencesAPI @Inject constructor(private val context: Context) {

    companion object {
        private const val PREFS = "MY_PREFS"
        private const val PAGE = "PAGE"
    }

    fun savePageIndex(page: Int) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putInt(PAGE, page).apply()
    }

    fun loadPageIndex(): Int {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getInt(PAGE, 1)
    }

}