package com.example.bookfinder.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
//    @SerialName(value = "totalItems") val total: Int,
    val kind: String,
    val totalItems: Int,
    val items: List<Book>,
)

@Serializable
data class Book(
    val id: String,
    val volumeInfo: Volume,
)

@Serializable
data class Volume(
    val title: String,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val averageRating: Int? = null,
    val ratingsCount: Int? = null,
    val imageLinks: Map<String, String>? = null,
    val language: String? = null,
    val canonicalVolumeLink: String? = null,
)