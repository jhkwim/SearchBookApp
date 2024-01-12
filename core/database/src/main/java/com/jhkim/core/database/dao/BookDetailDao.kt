package com.jhkim.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jhkim.core.database.model.BookDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookDetail(bookDetailEntity: BookDetailEntity): Long

    @Query(value = "SELECT * FROM book_detail WHERE isbn13 = :isbn13")
    fun getBookDetail(isbn13: String): Flow<List<BookDetailEntity>>
}