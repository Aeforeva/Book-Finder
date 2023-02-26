package com.example.bookfinder.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {

    @GET("volumes?q=cicero")
    suspend fun getCicero(): String

    @GET("volumes")
    suspend fun getBooks(@Query("q") book: String): String

    @GET("volumes/{id}") // 24yRRvkgsc8C
    suspend fun getBookById(@Path("id") id: String): String
}

private const val BASE_URL = "https://www.googleapis.com/books/v1/"

@OptIn(ExperimentalSerializationApi::class)
private val retrofit: Retrofit = Retrofit.Builder()
//        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

val retrofitService: BookApiService by lazy {
    retrofit.create(BookApiService::class.java)
}
