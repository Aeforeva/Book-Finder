package com.example.bookfinder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookfinder.R
import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun BookApp() {
    var bookTitle by remember { mutableStateOf("") }
    val viewModel: BookViewModel = viewModel()

    Column(modifier = Modifier.padding(8.dp)) {
        Row() {
            SearchBookField(
                value = bookTitle,
                onValueChanged = { bookTitle = it }
            )
            IconButton(
                onClick = { viewModel.getBooks(bookTitle) },
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .height(56.dp) // TODO make it equals SearchBookField
                    .aspectRatio(1f)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = stringResource(R.string.search_button),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "uiState:")
        MainScreen(viewModel.bookUiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBookField(
    value: String,
    onValueChanged: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.book_title)) },
//        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun MainScreen(bookUiState: BookUiState) {
    when (bookUiState) {
        is BookUiState.Input -> Text(text = "Input")
        is BookUiState.Loading -> Text(text = "loading")
        is BookUiState.Success -> Text(text = bookUiState.books)
        is BookUiState.Error -> Text(text = bookUiState.error)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BookFinderTheme {
        BookApp()
    }
}