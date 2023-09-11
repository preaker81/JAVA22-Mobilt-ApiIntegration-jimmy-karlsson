package com.example.apiintigration.api

import retrofit2.Call
import retrofit2.http.GET
import com.example.apiintigration.data.ScryfallResponse

interface ScryfallApi {
    @GET("cards/random")
    fun getRandomCard(): Call<ScryfallResponse>
}
