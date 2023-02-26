package com.example.bookfinder.data

import com.example.bookfinder.network.BookApiService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class AppContainer {

    val bookRepository: BookRepository by lazy { BookRepository(retrofitService) }

    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}