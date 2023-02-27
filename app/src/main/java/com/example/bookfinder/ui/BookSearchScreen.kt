package com.example.bookfinder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun BookSearchScreen(
    searchedBookTitle: String,
    onValueChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            SearchBookField(
                value = searchedBookTitle,
                onValueChanged = { onValueChanged(it) },
                onKeyboardDone = { onSearch(searchedBookTitle) }
            )
            IconButton(
                onClick = { onSearch(searchedBookTitle) },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBookField(
    value: String,
    onValueChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookSearchScreenPreview() {
    BookFinderTheme {
        BookSearchScreen("", {}, {})
    }
}