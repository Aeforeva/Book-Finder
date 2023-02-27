package com.example.bookfinder.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun BookApp() {

    val viewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)

    Column(modifier = Modifier.padding(8.dp)) {
        BookSearchScreen(viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "uiState:")
        BookMainScreen(viewModel.bookUiState)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookFinderTheme {
        BookApp()
    }
}