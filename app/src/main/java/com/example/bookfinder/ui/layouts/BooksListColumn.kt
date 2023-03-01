package com.example.bookfinder.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookfinder.R
import com.example.bookfinder.model.Book
import com.example.bookfinder.model.SearchResult
import com.example.bookfinder.model.testBook
import com.example.bookfinder.model.testSearchResult
import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun BooksListColumn(
    searchResult: SearchResult,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(
            count = searchResult.items.size,
            key = { index -> searchResult.items[index].id },
            contentType = { Book } // ?
        ) { index ->
            BookLineCard(book = searchResult.items[index], onBookSelected = onBookSelected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BookLineCard(
    book: Book,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(187.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = { onBookSelected(book) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            val imageUrl = book.volumeInfo.imageLinks?.get("thumbnail")
                ?: book.volumeInfo.imageLinks?.get("smallThumbnail")
            AsyncImage(
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(171.dp)
                    .width(128.dp)
                    .padding(end = 8.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillBounds,
                error = painterResource(id = R.drawable.book_placeholder),
                placeholder = painterResource(id = R.drawable.book_placeholder)
            )
            Column() {
                val topPadding = Modifier.padding(top = 8.dp)
                Text(
                    text = book.volumeInfo.title,
                    fontWeight = FontWeight.Bold,
                    modifier = topPadding
                )
                book.volumeInfo.authors?.let {
                    val authors = it.joinToString(", ")
                    Text(
                        text = pluralStringResource(R.plurals.authors, it.size, authors),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        modifier = topPadding
                    )
                }
                book.volumeInfo.subtitle?.let {
                    Text(
                        text = it,
                        modifier = topPadding
                    )
                }
                book.volumeInfo.publisher?.let {
                    Text(
                        text = stringResource(R.string.publisher, it),
                        modifier = topPadding
                    )
                }
                book.volumeInfo.publishedDate?.let {
                    Text(
                        text = stringResource(R.string.published_date, it),
                        modifier = topPadding
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BooksListColumnPreview() {
    BookFinderTheme {
        BooksListColumn(testSearchResult, {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookLineCardPreview() {
    BookFinderTheme {
        BookLineCard(testBook, {})
    }
}