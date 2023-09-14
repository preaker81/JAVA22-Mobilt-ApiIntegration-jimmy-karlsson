package com.example.apiintigration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.apiintigration.databinding.FragmentRandomJokeBinding
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class RandomJokeFragment : Fragment() {

    private lateinit var binding: FragmentRandomJokeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomJokeBinding.inflate(inflater, container, false)

        binding.generateJokeButton.setOnClickListener {
            thread {
                val url = URL("https://official-joke-api.appspot.com/random_joke")
                val connection = url.openConnection() as HttpURLConnection

                try {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))

                    val response = reader.readText()

                    val jsonParser = JSONParser()
                    val jsonObject = jsonParser.parse(response) as JSONObject

                    val setup = jsonObject["setup"] as String
                    val punchline = jsonObject["punchline"] as String

                    activity?.runOnUiThread {
                        binding.setupText.text = setup
                        binding.punchlineText.text = punchline
                    }

                } finally {
                    connection.disconnect()
                }
            }
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
