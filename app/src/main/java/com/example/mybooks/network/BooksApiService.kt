package com.example.mybooks.network

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://granlof.hopto.org/books/writers/books/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

interface BooksApiService {
    @GET(".")
    fun getBooks(): JsonObject
}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}