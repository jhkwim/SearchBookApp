package com.jhkim.core.data.repository

import com.jhkim.core.model.Book
import com.jhkim.core.model.BookDetail
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    val books: Flow<List<Book>>

    suspend fun searchBook(query: String)

    suspend fun getBookDetail(isbn13: String): Flow<BookDetail>

    suspend fun clear()

}