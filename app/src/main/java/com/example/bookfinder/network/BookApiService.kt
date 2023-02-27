package com.example.bookfinder.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {

    @GET("volumes?q=cicero")
    suspend fun getCicero(): String

    @GET("volumes") // volumes?q={bookTitle}
    suspend fun getBooks(@Query("q") bookTitle: String): String

    @GET("volumes/{id}") // volumes/24yRRvkgsc8C
    suspend fun getBookById(@Path("id") id: String): String
}
