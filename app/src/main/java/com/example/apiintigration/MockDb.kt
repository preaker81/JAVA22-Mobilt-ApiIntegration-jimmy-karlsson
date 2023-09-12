package com.example.apiintigration

import android.content.SharedPreferences

class MockDb {
    companion object {
        var isLoggedIn = false
        private val username = "user"
        private val password = "pass"

        fun checkLoggIn(
            username: String,
            password: String,
            sharedPreferences: SharedPreferences
        ): Boolean {
            val result = username == this.username && password == this.password
            if (result) {
                isLoggedIn = true
                with(sharedPreferences.edit()) {
                    putBoolean("isLoggedIn", true)
                    apply()
                }
            }
            return result
        }

        fun handleLogout(sharedPreferences: SharedPreferences) {
            isLoggedIn = false
            with(sharedPreferences.edit()) {
                putBoolean("isLoggedIn", false)
                apply()
            }
        }
    }
}
