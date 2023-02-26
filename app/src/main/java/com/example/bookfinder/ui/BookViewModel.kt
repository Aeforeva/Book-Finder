package com.example.bookfinder.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookUiState {
    object Input : BookUiState
    object Loading : BookUiState
    data class Success(val Books: String) : BookUiState
    object Error : BookUiState
}

class BookViewModel() : ViewModel() {

    var bookUiState: BookUiState by mutableStateOf(BookUiState.Input)
        private set

    //TODO This is testing options
    fun getBooks(book: String) {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                BookUiState.Success(book)
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error
            }
        }
    }
}