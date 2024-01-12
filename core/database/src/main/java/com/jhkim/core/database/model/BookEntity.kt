package com.jhkim.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhkim.core.model.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val isbn13: String,
    val image: String,
    val price: String,
    val subtitle: String,
    val title: String,
    val url: String
)

fun BookEntity.toBookDetail() = Book(
    isbn13 = isbn13,
    image = image,
    price = price,
    subtitle = subtitle,
    title = title,
    url = url
)