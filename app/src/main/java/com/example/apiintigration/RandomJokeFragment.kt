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
import com.example.apiintigration.databinding.FragmentRandomJokeBinding
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

class RandomJokeFragment : Fragment() {

    private lateinit var binding: FragmentRandomJokeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomJokeBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(JokeApi::class.java)

        binding.generateJokeButton.setOnClickListener {
            api.getRandomJoke().enqueue(object : Callback<JokeResponse> {
                override fun onResponse(call: Call<JokeResponse>, response: Response<JokeResponse>) {
                    response.body()?.let {
                        binding.setupText.text = it.setup
                        binding.punchlineText.text = it.punchline
                    }
                }

                override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        }

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        binding.rndJokeLogoutBtn.setOnClickListener {
            MockDb.handleLogout(sharedPreferences)

            // Create NavOptions to clear back stack
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.LoginFragment, true)
                .build()

            findNavController().navigate(R.id.LoginFragment, null, navOptions)
        }

        return binding.root
    }
}
