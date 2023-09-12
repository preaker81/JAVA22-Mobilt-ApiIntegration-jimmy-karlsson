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
import com.example.apiintigration.data.ScryfallResponse
import com.example.apiintigration.databinding.FragmentRandomMtgCardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomMtgCardFragment : Fragment() {
    private var _binding: FragmentRandomMtgCardBinding? = null
    private val binding get() = _binding!!

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

        // Retrofit setup
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.scryfall.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ScryfallApi::class.java)

        // Initial API call to load image
        loadRandomImage(api)

        // Set click listener to ImageView to reload the image
        binding.imageView.setOnClickListener {
            loadRandomImage(api)
        }

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Logout button functionality
        binding.logoutButton.setOnClickListener {
            MockDb.handleLogout(sharedPreferences)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build()
            findNavController().navigate(R.id.LoginFragment, null, navOptions)
        }
    }

    private fun loadRandomImage(api: ScryfallApi) {
        // API call
        api.getRandomCard().enqueue(object : Callback<ScryfallResponse> {
            override fun onResponse(
                call: Call<ScryfallResponse>,
                response: Response<ScryfallResponse>
            ) {
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.image_uris?.border_crop
                    imageUrl?.let { url ->
                        Glide.with(requireContext()).load(url).into(binding.imageView)
                        binding.urlEditText.setText(url)
                    }
                }
            }

            override fun onFailure(call: Call<ScryfallResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch random card", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
