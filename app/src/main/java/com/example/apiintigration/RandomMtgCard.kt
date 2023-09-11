package com.example.apiintigration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.apiintigration.api.ScryfallApi
import com.example.apiintigration.data.ScryfallResponse
import com.example.apiintigration.databinding.ActivityRandomMtgCardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomMtgCard : AppCompatActivity() {
    private lateinit var binding: ActivityRandomMtgCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomMtgCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    private fun loadRandomImage(api: ScryfallApi) {
        // API call
        api.getRandomCard().enqueue(object : Callback<ScryfallResponse> {
            override fun onResponse(call: Call<ScryfallResponse>, response: Response<ScryfallResponse>) {
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.image_uris?.border_crop
                    imageUrl?.let { url ->
                        // Use Glide to load the image into the ImageView
                        Glide.with(this@RandomMtgCard).load(url).into(binding.imageView)
                    }
                }
            }

            override fun onFailure(call: Call<ScryfallResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}
