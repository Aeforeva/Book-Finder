package com.example.bookfinder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R

@Composable
fun BookSearchScreen(viewModel: BookViewModel) {
    var bookTitle by remember { mutableStateOf("") }

    Row(verticalAlignment = Alignment.Bottom) {
        SearchBookField(
            value = bookTitle,
            onValueChanged = { bookTitle = it },
            onKeyboardDone = { viewModel.getBooks(bookTitle) }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBookField(
    value: String = "",
    onValueChanged: (String) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.book_title)) },
//        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onKeyboardDone() }
        ),
    )
}