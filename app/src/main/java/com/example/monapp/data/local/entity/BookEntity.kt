package com.example.monapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monapp.models.Book

@Entity(tableName = "favorite_books")
data class BookEntity(
    @PrimaryKey
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author_names")
    val authorNames: String,

    @ColumnInfo(name = "first_publish_year")
    val firstPublishYear: Int?,

    @ColumnInfo(name = "cover_id")
    val coverId: Int?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis()
)

fun Book.toEntity(): BookEntity {
    return BookEntity(
        title = this.title,
        authorNames = this.authorName.joinToString(","),
        firstPublishYear = this.firstPublishYear,
        coverId = this.coverId,
        description = this.description
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        title = this.title,
        authorName = if (this.authorNames.isEmpty()) emptyList() else this.authorNames.split(","),
        firstPublishYear = this.firstPublishYear,
        coverId = this.coverId,
        description = this.description
    )
}