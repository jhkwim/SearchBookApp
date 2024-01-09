package com.jhkim.core.data.repository

import com.jhkim.core.model.Book
import com.jhkim.core.model.BookDetail
import com.jhkim.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class BookRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : BookRepository {

    override suspend fun searchBook(query: String, page: Int) = flow {
        val networkSearchResult = networkDataSource.searchBook(query, page)
        require(networkSearchResult.error == "0") { networkSearchResult.error }
        emit(networkSearchResult)
    }.map { networkSearchResult ->
        networkSearchResult.books.map {
            Book(
                isbn13 = it.isbn13,
                image = it.image,
                price = it.price,
                subtitle = it.subtitle,
                title = it.title,
                url = it.url
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getBookDetail(isbn: String) = flow {
        val networkBookDetail = networkDataSource.getBookDetail(isbn)
        require(networkBookDetail.error == "0") { networkBookDetail.error }
        emit(networkBookDetail)
    }.map {
        BookDetail(
            isbn13 = it.isbn13,
            authors = it.authors,
            desc = it.desc,
            image = it.image,
            isbn10 = it.isbn10,
            language = it.language,
            pages = it.pages,
            price = it.price,
            publisher = it.publisher,
            rating = it.rating,
            subtitle = it.subtitle,
            title = it.title,
            url = it.url,
            year = it.year
        )
    }.flowOn(Dispatchers.IO)

}