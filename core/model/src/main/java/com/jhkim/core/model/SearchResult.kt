package com.jhkim.core.model


data class SearchResult(
    val books: List<Book>,
    val error: String,
    val page: String,
    val total: String
)

data class Book(
    val isbn13: String,
    val image: String,
    val price: String,
    val subtitle: String,
    val title: String,
    val url: String
)