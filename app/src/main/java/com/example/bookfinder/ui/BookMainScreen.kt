package com.example.bookfinder.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.model.*
import com.example.bookfinder.ui.layouts.BookLineCard
import com.example.bookfinder.ui.layouts.BooksListColumn
import com.example.bookfinder.ui.layouts.BooksListGrid
import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun BookMainScreen(
    bookUiState: BookUiState,
    retryAction: () -> Unit,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (bookUiState) {
        is BookUiState.Loading -> LoadingScreen()
//        is BookUiState.Success -> BooksListGrid(bookUiState.searchResult, onBookSelected)
        is BookUiState.Success -> BooksListColumn(bookUiState.searchResult, onBookSelected)
        is BookUiState.Error -> ErrorScreen(bookUiState.error, retryAction)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .size(600.dp)
                .rotate(angle),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(error: String, retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookMainScreenPreview() {
    BookFinderTheme {
        BookMainScreen(BookUiState.Success(testSearchResult), {}, {})
    }
}
