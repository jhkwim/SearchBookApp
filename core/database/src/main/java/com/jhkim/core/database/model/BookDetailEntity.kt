package com.jhkim.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhkim.core.model.BookDetail

@Entity(tableName = "book_detail")
data class BookDetailEntity(
    @PrimaryKey val isbn13: String,
    val authors: String,
    val desc: String,
    val image: String,
    val isbn10: String,
    val language: String,
    val pages: String,
    val price: String,
    val publisher: String,
    val rating: String,
    val subtitle: String,
    val title: String,
    val url: String,
    val year: String
)

fun BookDetailEntity.toBookDetail() = BookDetail(
    isbn13 = isbn13,
    authors = authors,
    desc = desc,
    image = image,
    isbn10 = isbn10,
    language = language,
    pages = pages,
    price = price,
    publisher = publisher,
    rating = rating,
    subtitle = subtitle,
    title = title,
    url = url,
    year = year
)
