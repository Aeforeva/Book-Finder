package com.example.bookfinder.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.bookfinder.model.testBook
import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun BookDetailScreen(book: Book) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
            ) {
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
        book.volumeInfo.description?.let { Text(text = it, Modifier.padding(top = 8.dp)) }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailBookPreview() {
    BookFinderTheme() {
        BookDetailScreen(testBook)
    }
}