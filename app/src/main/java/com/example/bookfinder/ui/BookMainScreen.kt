package com.example.bookfinder.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BookMainScreen(bookUiState: BookUiState, onBookSelected: () -> Unit) {
    when (bookUiState) {
        is BookUiState.Input -> Text(text = "Input")
        is BookUiState.Loading -> Text(text = "loading")
        is BookUiState.Success -> Text(text = bookUiState.books)
        is BookUiState.Error -> Text(text = bookUiState.error)
    }
}