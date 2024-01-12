package com.jhkim.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jhkim.core.database.model.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreBooks(bookEntities: List<BookEntity>): List<Long>

    @Query(value = "SELECT * FROM books")
    fun getBookEntities(): Flow<List<BookEntity>>

    @Query(value = "DELETE FROM books")
    suspend fun deleteBooks()

}