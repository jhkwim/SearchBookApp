package com.jhkim.core.model


data class BookDetail(
    val isbn13: String,
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