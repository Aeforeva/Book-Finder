package com.example.bookfinder.ui.layouts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

/**
 * I choose to use BookListColumn for now.
 **/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BooksListGrid(
    searchResult: SearchResult,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(
            count = searchResult.items.size,
//            items = searchResult.items,
            key = { index -> searchResult.items[index].id },
            contentType = { Book } // ?
        ) { index ->
            BookSmallCard(book = searchResult.items[index], onBookSelected = onBookSelected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSmallCard(
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
                    .data(imageUrl?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.book_placeholder),
                placeholder = painterResource(id = R.drawable.book_placeholder)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BooksListGridPreview() {
    BookFinderTheme {
        BooksListGrid(testSearchResult, {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookSmallCardPreview() {
    BookFinderTheme {
        BookSmallCard(testBook, {})
    }
}