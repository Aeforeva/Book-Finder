package com.example.bookfinder.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.network.retrofitService
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookUiState {
    object Input : BookUiState
    object Loading : BookUiState
    data class Success(val books: String) : BookUiState
    data class Error(val error: String) : BookUiState
}

class BookViewModel() : ViewModel() {

    var bookUiState: BookUiState by mutableStateOf(BookUiState.Input)
        private set

    //TODO This is testing options
    fun getBooks(book: String) {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                BookUiState.Success(retrofitService.getBooks(book))
//                BookUiState.Success(retrofitService.getBookById("24yRRvkgsc8C"))
            } catch (e: IOException) {
                BookUiState.Error(e.toString())
            } catch (e: HttpException) {
                BookUiState.Error(e.toString())
            }
        }
    }
}