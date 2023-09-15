package com.example.apiintigration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apiintigration.api.ScryfallApi
import com.example.apiintigration.data.MockDb
import com.example.apiintigration.data.ScryfallResponse
import com.example.apiintigration.databinding.FragmentRandomMtgCardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomMtgCardFragment : Fragment() {

    // Declare nullable binding variable and non-nullable custom getter
    private var _binding: FragmentRandomMtgCardBinding? = null
    private val binding get() = _binding!!

    // Initialize the fragment view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomMtgCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.scryfall.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ScryfallApi::class.java)

        // Make an initial API call to populate the view
        loadRandomImage(api)

        // Reload image on click
        binding.imageView.setOnClickListener {
            loadRandomImage(api)
        }

        // Access shared preferences
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Logout functionality
        binding.rndMtgLogoutBtn.setOnClickListener {
            MockDb.handleLogout(sharedPreferences)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build()
            findNavController().navigate(R.id.LoginFragment, null, navOptions)
        }
    }

    // Helper function to load image from API
    private fun loadRandomImage(api: ScryfallApi) {
        api.getRandomCard().enqueue(object : Callback<ScryfallResponse> {
            override fun onResponse(
                call: Call<ScryfallResponse>,
                response: Response<ScryfallResponse>
            ) {
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.image_uris?.border_crop
                    imageUrl?.let { url ->
                        // Load image using Glide
                        Glide.with(requireContext()).load(url).into(binding.imageView)
                        // Set the URL into the EditText
                        binding.urlEditText.setText(url)
                    }
                }
            }

            override fun onFailure(call: Call<ScryfallResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch random card", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Nullify the binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
