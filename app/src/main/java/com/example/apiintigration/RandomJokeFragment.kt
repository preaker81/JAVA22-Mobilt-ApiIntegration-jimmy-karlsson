package com.example.apiintigration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.apiintigration.api.JokeApi
import com.example.apiintigration.api.JokeResponse
import com.example.apiintigration.data.MockDb
import com.example.apiintigration.databinding.FragmentRandomJokeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomJokeFragment : Fragment() {

    // Declare View Binding variable
    private lateinit var binding: FragmentRandomJokeBinding

    // Initialize the fragment view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize View Binding
        binding = FragmentRandomJokeBinding.inflate(inflater, container, false)

        // Initialize Retrofit with Gson converter and base URL
        val retrofit = Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an API interface instance from Retrofit
        val api = retrofit.create(JokeApi::class.java)

        // Setup button click listener to fetch a random joke
        binding.generateJokeButton.setOnClickListener {
            api.getRandomJoke().enqueue(object : Callback<JokeResponse> {
                // Handle API response
                override fun onResponse(
                    call: Call<JokeResponse>,
                    response: Response<JokeResponse>
                ) {
                    // Update UI with the joke data if available
                    response.body()?.let {
                        binding.setupText.text = it.setup
                        binding.punchlineText.text = it.punchline
                    }
                }

                // Handle API failure
                override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                    // Handle failure (perhaps show a message to the user)
                }
            })
        }

        // Access shared preferences
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Setup Logout button click listener
        binding.rndJokeLogoutBtn.setOnClickListener {
            // Perform logout actions
            MockDb.handleLogout(sharedPreferences)

            // Setup NavOptions to clear the back stack
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.LoginFragment, true)
                .build()

            // Navigate back to Login Fragment
            findNavController().navigate(R.id.LoginFragment, null, navOptions)
        }

        return binding.root
    }
}
