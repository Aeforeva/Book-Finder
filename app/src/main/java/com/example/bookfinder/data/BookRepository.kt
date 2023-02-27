package com.example.bookfinder.data

import com.example.bookfinder.model.Book
import com.example.bookfinder.model.SearchResult
import com.example.bookfinder.model.testBook
import com.example.bookfinder.network.BookApiService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BookRepository(private val bookApiService: BookApiService) {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Note: If app have local database we can use it [localTestBook],
     * and then update local database record with [syncData] from network if available
     * */
    var localTestBook = testBook // testBook from DB, id = 24yRRvkgsc8C
    suspend fun syncData() {
        val bookById = bookApiService.getBookById("24yRRvkgsc8C") // String search result
        val updatedTestBook = json.decodeFromString<Book>(bookById)
        testBook = updatedTestBook
//        If we want save testBook as string we can encode it back so we won't have redundant keys
//        val updateTestBookAsString = Json.encodeToString(updatedTestBook)
    }

    suspend fun getBooks(bookTitle: String): SearchResult {
        val searchResultString = bookApiService.getBooks(bookTitle)
        return json.decodeFromString<SearchResult>(searchResultString)
    }

    suspend fun getBookById(id: String): Book {
        val bookByIdString = bookApiService.getBookById(id)
        return json.decodeFromString<Book>(bookByIdString)
    }
}