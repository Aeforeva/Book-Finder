package com.example.bookfinder.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookfinder.BookApplication
import com.example.bookfinder.data.BookRepository
import com.example.bookfinder.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookUiState {
    object Loading : BookUiState
    data class Success(val searchResult: SearchResult) : BookUiState
    data class Error(val error: String) : BookUiState
}

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    private val _selectedBook = MutableStateFlow(Book(id = "0", volumeInfo = testVolume))
    val selectedBook: StateFlow<Book> = _selectedBook

    fun getBooks(bookTitle: String) {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                val searchResult = bookRepository.getBooks(bookTitle)
                selectBook(searchResult.items[0])
                BookUiState.Success(searchResult)
            } catch (e: IOException) {
                BookUiState.Error(e.toString())
            } catch (e: HttpException) {
                BookUiState.Error(e.toString())
            }
        }
    }

    fun selectBook(book: Book) {
        _selectedBook.update {
            it.copy(
                id = book.id,
                volumeInfo = book.volumeInfo
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookApplication)
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
}