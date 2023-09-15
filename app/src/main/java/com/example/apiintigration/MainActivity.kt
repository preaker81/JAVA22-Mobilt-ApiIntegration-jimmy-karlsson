package com.example.apiintigration

// Android and Jetpack libraries
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiintigration.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

// Define the MainActivity class which extends AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Declare variables for ActivityMainBinding and NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // Override the onCreate method, which is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ActivityMainBinding and set it as the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavHostFragment that hosts navigation and get its NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}
