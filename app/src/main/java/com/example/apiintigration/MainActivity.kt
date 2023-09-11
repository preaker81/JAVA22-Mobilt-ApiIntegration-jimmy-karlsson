package com.example.apiintigration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiintigration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        // Check logged in status from SharedPreferences
        MockDb.isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // If already logged in, navigate to Dashboard
        if (MockDb.isLoggedIn) {
            val dashboardIntent = Intent(this, Dashboard::class.java)
            startActivity(dashboardIntent)
            finish() // Optional: finish MainActivity so user can't go back to it
            return
        }

        updateUI()

        // Login Button
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val loggedIn = MockDb.checkLoggIn(username, password)
            if (loggedIn) {
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                updateUI()

                // Navigate to Dashboard
                val dashboardIntent = Intent(this, Dashboard::class.java)
                startActivity(dashboardIntent)
            }
        }

        // Logout Button
        binding.logoutButton.setOnClickListener {
            MockDb.isLoggedIn = false
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            updateUI()
        }
    }

    private fun updateUI() {
        val isLoggedIn = MockDb.isLoggedIn
        binding.usernameEditText.isEnabled = !isLoggedIn
        binding.passwordEditText.isEnabled = !isLoggedIn
        binding.loginButton.isEnabled = !isLoggedIn
    }
}



// https://api.scryfall.com/cards/random