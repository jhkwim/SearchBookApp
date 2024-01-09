package com.jhkim.core.data.repository

import com.jhkim.core.model.Book
import com.jhkim.core.model.BookDetail
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun searchBook(query: String, page: Int): Flow<List<Book>>

    suspend fun getBookDetail(isbn: String): Flow<BookDetail>

}