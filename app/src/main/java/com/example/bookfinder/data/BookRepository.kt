package com.example.bookfinder.data

import com.example.bookfinder.network.BookApiService

class BookRepository(private val bookApiService: BookApiService) {

    suspend fun getCicero(): String {
        return bookApiService.getCicero()
    }

    suspend fun getBooks(bookTitle: String): String {
        return bookApiService.getBooks(bookTitle)
    }

    suspend fun getBookById(id: String): String {
        return bookApiService.getBookById(id)
    }
}