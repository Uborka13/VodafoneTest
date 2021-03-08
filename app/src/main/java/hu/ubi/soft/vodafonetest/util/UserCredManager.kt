package hu.ubi.soft.vodafonetest.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class UserCredManager @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("VodApp", Context.MODE_PRIVATE)

    fun saveUserName(loginName: String) {
        sharedPreferences.edit().putString(USER_NAME_KEY, loginName).apply()
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, token).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun logout() {
        sharedPreferences.edit().also {
            it.remove(USER_NAME_KEY)
            it.remove(ACCESS_TOKEN_KEY)
            it.remove(REFRESH_TOKEN_KEY)
        }.apply()
    }

    companion object {
        const val USER_NAME_KEY = "username_key"
        const val ACCESS_TOKEN_KEY = "access_token_key"
        const val REFRESH_TOKEN_KEY = "refresh_token_key"
    }

}