package com.example.apiintigration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiintigration.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        // Log Out Button
        binding.dashLoggoutBtn.setOnClickListener {
            // Update Shared Preferences
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

            // Navigate to MainActivity
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mainActivityIntent)
            finish()
        }

        // Random MTG Card Button
        binding.randomMtgButton.setOnClickListener {
            // Navigate to RandomMtgCard
            val randomMtgIntent = Intent(this, RandomMtgCard::class.java)
            startActivity(randomMtgIntent)
        }
    }
}
