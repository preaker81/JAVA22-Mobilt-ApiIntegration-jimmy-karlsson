package com.example.apiintigration.api

import retrofit2.Call
import retrofit2.http.GET

interface JokeApi {
    @GET("random_joke")
    fun getRandomJoke(): Call<JokeResponse>
}

data class JokeResponse(
    val type: String,
    val setup: String,
    val punchline: String,
    val id: Int
)
