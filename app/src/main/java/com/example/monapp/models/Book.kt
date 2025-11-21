package com.example.monapp.models

data class Book(
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?,
    val cover_i: Int?,
    val description: Any?
) {
    val cover_url: String?
        get() = cover_i?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" }
}
