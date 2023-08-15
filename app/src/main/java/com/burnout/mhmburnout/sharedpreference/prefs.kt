package com.burnout.mhmburnout.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class prefs(context: Context) {
    private val KEY_1 = "key1"
    private val KEY_2 = "key2"
    private val KEY_3 = "key3"

    private val preferences1: SharedPreferences = context.getSharedPreferences(KEY_1, Context.MODE_PRIVATE)
    private val preferences2: SharedPreferences = context.getSharedPreferences(KEY_2, Context.MODE_PRIVATE)
    private val preferences3: SharedPreferences = context.getSharedPreferences(KEY_3, Context.MODE_PRIVATE)

    fun saveBool(value: Boolean){
        val editor : SharedPreferences.Editor = preferences1.edit()
        editor.putBoolean(KEY_1, value)
        editor.commit()
    }
    fun saveDay(value: Int){
        val editor : SharedPreferences.Editor = preferences2.edit()
        editor.putInt(KEY_2, value)
        editor.commit()
    }
    fun saveDate(value: Long){
        val editor : SharedPreferences.Editor = preferences3.edit()
        editor.putLong(KEY_3, value)
        editor.commit()
    }

    fun getBool(): Boolean{
        return preferences1.getBoolean(KEY_1, false)
    }
    fun getDay(): Int{
        return preferences2.getInt(KEY_2, 1)
    }
    fun getDate():Long{
        return preferences3.getLong(KEY_3, 0)
    }
}