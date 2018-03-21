package id.aasumitro.easynote.util

import android.content.SharedPreferences
import javax.inject.Inject


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class SharedPrefs @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun putBoolean(key: String, value: Boolean): Boolean {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean =
            sharedPreferences.getBoolean(key, defaultValue)

    fun putString(key: String, value: String): Boolean {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getString(key: String, defaultValue: String): String =
            sharedPreferences.getString(key, defaultValue)

    fun clearPrefs() = sharedPreferences.edit().clear().clear().apply()

}