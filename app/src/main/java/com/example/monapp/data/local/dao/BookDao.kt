package com.example.monapp.data.local.dao

import androidx.room.*
import com.example.monapp.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM favorite_books ORDER BY added_at DESC")
    fun getAllFavorites(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(book: BookEntity)

    @Query("DELETE FROM favorite_books WHERE title = :title")
    suspend fun deleteFavoriteByTitle(title: String)

    @Query("DELETE FROM favorite_books")
    suspend fun deleteAllFavorites()

    @Query("SELECT COUNT(*) FROM favorite_books")
    fun getFavoritesCount(): Flow<Int>
}