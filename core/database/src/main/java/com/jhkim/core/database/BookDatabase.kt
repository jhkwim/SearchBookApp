package com.jhkim.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jhkim.core.database.dao.BookDao
import com.jhkim.core.database.dao.BookDetailDao
import com.jhkim.core.database.model.BookDetailEntity
import com.jhkim.core.database.model.BookEntity
import com.jhkim.core.model.BookDetail

@Database(entities = [BookEntity::class, BookDetailEntity::class], version = 1)
internal abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun bookDetailDao(): BookDetailDao

}