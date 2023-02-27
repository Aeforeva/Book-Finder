package com.example.bookfinder.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookfinder.model.Book
import com.example.bookfinder.model.testBook
import com.example.bookfinder.ui.theme.BookFinderTheme
import com.example.bookfinder.R
import com.example.bookfinder.model.SearchResult
import com.example.bookfinder.model.testSearchResult

@Composable
fun BookMainScreen(
    bookUiState: BookUiState,
    retryAction: () -> Unit,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (bookUiState) {
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Success -> BooksPreviewList(bookUiState.searchResult, onBookSelected)
        is BookUiState.Error -> ErrorScreen(bookUiState.error, retryAction)
    }
}

@Composable
fun BooksPreviewList(
    searchResult: SearchResult,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(
            items = searchResult.items,
//            key = { photo -> photo.id }
        ) { book ->
            BookPreviewCard(book = book, onBookSelected = onBookSelected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookPreviewCard(
    book: Book,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
//            .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = { onBookSelected(book) }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            val imageUrl = book.volumeInfo.imageLinks?.get("thumbnail")
                ?: book.volumeInfo.imageLinks?.get("smallThumbnail")
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = book.volumeInfo.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = book.volumeInfo.authors?.get(0) ?: "Unknown",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 8.dp)
            )
            book.volumeInfo.subtitle?.let { Text(text = it, Modifier.padding(top = 8.dp)) }
            book.volumeInfo.publisher?.let { Text(text = it, Modifier.padding(top = 8.dp)) }
            book.volumeInfo.publishedDate?.let { Text(text = it, Modifier.padding(top = 8.dp)) }
        }
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
fun BooksListPreview() {
    BookFinderTheme {
        BooksPreviewList(testSearchResult, {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookCardPreview() {
    BookFinderTheme {
        BookPreviewCard(testBook, {})
    }
}