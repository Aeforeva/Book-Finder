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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookUiState {
    object Input : BookUiState
    object Loading : BookUiState
    data class Success(val books: String) : BookUiState
    data class Error(val error: String) : BookUiState
}

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    var bookUiState: BookUiState by mutableStateOf(BookUiState.Input)
        private set

    fun getBooks(bookTitle: String) {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                val searchResult = bookRepository.getBooks(bookTitle)
//                val bookById = bookRepository.getBookById("24yRRvkgsc8C")
                BookUiState.Success(searchResult.toString())
            } catch (e: IOException) {
                BookUiState.Error(e.toString())
            } catch (e: HttpException) {
                BookUiState.Error(e.toString())
            }
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