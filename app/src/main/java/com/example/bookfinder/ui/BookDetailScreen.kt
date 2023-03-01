package com.example.bookfinder.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookDetailScreen(book: Book, modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
    ) {
        val imageUrl = book.volumeInfo.imageLinks?.get("thumbnail")
            ?: book.volumeInfo.imageLinks?.get("smallThumbnail")
        AsyncImage(
            alignment = Alignment.Center,
            modifier = Modifier.height((171*2).dp).fillMaxWidth(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl?.replace("http", "https"))
                .crossfade(true)
                .build(),
            contentDescription = book.volumeInfo.title,
//            contentScale = ContentScale.Fit,
            error = painterResource(id = R.drawable.book),
            placeholder = painterResource(id = R.drawable.book_placeholder)
        )
        val topPadding = Modifier.padding(top = 8.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = book.volumeInfo.title,
                fontWeight = FontWeight.Bold
            )
            if (book.volumeInfo.averageRating != null && book.volumeInfo.ratingsCount != null) {
                Text(
                    text = stringResource(
                        R.string.rating,
                        book.volumeInfo.averageRating,
                        book.volumeInfo.ratingsCount
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        book.volumeInfo.authors?.let {
            val authors = it.joinToString(", ")
            Text(
                text = pluralStringResource(R.plurals.authors, it.size, authors),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = topPadding
            )
        }
        book.volumeInfo.subtitle?.let { Text(text = it, modifier = topPadding) }
        book.volumeInfo.description?.let { Text(text = it, modifier = topPadding) }
        book.volumeInfo.canonicalVolumeLink?.let { url ->
            val context = LocalContext.current
            Text(
                text = AnnotatedString(url),
                color = Color.Blue,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { openExternalLink(context, url) }
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
        book.volumeInfo.pageCount?.let {
            Text(
                text = stringResource(R.string.page_count, it),
                modifier = topPadding
            )
        }
        book.volumeInfo.language?.let {
            Text(
                text = stringResource(R.string.language, it),
                modifier = topPadding
            )
        }
    }
}

fun openExternalLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.open_in)
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookDetailScreenPreview() {
    BookFinderTheme() {
        BookDetailScreen(testBook)
    }
}