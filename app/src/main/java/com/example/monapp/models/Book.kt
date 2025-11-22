package com.example.monapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("title")
    val title: String = "",

    @SerialName("author_name")
    val authorName: List<String> = emptyList(),

    @SerialName("first_publish_year")
    val firstPublishYear: Int? = null,

    @SerialName("cover_i")
    val coverId: Int? = null,

    @SerialName("description")
    val description: String? = null
) {
    val coverUrl: String?
        get() = coverId?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" }
}
