package com.jhkim.core.data.repository

import com.jhkim.core.database.dao.BookDao
import com.jhkim.core.database.dao.BookDetailDao
import com.jhkim.core.database.model.BookDetailEntity
import com.jhkim.core.database.model.BookEntity
import com.jhkim.core.database.model.toBookDetail
import com.jhkim.core.model.BookDetail
import com.jhkim.core.network.NetworkDataSource
import com.jhkim.core.network.model.NetworkBook
import com.jhkim.core.network.model.NetworkBookDetail
import com.jhkim.core.network.model.toBookDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class BookRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val bookDao: BookDao,
    private val bookDetailDao: BookDetailDao
) : BookRepository {

    private var currentQuery = ""
    private var page = 1

    override val books = bookDao.getBookEntities().map {
        it.map { bookEntity -> bookEntity.toBookDetail() }
    }

    override suspend fun searchBook(query: String) {
       if (isNewQuery(query)) {
            currentQuery = query
            page = 1
            bookDao.deleteBooks()
        }

        val books = when {
            currentQuery.contains("|") -> searchTwoBooks()
            currentQuery.contains("-") -> filterSearchBooks()
            else -> internalSearchBook(currentQuery, page)
        }

        bookDao.insertOrIgnoreBooks(books.map {
            BookEntity(
                isbn13 = it.isbn13,
                image = it.image,
                price = it.price,
                subtitle = it.subtitle,
                title = it.title,
                url = it.url
            )
        })

        page++
    }

    private suspend fun searchTwoBooks(): List<NetworkBook> = withContext(Dispatchers.IO) {
        val queries = currentQuery.split("|")
        val deferred1 = async { networkDataSource.searchBook(queries[0], page) }
        val deferred2 = async { networkDataSource.searchBook(queries[1], page) }

        val networkSearchResult1 = deferred1.await()
        val networkSearchResult2 = deferred2.await()

        require(networkSearchResult1.error == "0" || networkSearchResult2.error == "0") {
            "${networkSearchResult1.error} || ${networkSearchResult2.error}"
        }

        val books = networkSearchResult1.books + networkSearchResult2.books
        require(books.isNotEmpty()) { "search result is empty" }

        books
    }

    private suspend fun filterSearchBooks(): List<NetworkBook> {
        val queries = currentQuery.split("-")

        return internalSearchBook(queries[0], page).filter {
            !it.title.contains(queries[1], ignoreCase = true)
        }
    }

    private suspend fun internalSearchBook(query: String, page: Int): List<NetworkBook> {
        val networkSearchResult = networkDataSource.searchBook(query, page)
        require(networkSearchResult.error == "0") { networkSearchResult.error }
        require(networkSearchResult.books.isNotEmpty()) { "search result is empty" }
        return networkSearchResult.books
    }

    private fun isNewQuery(query: String) = query.isNotBlank() && this.currentQuery != query

    override suspend fun getBookDetail(isbn13: String) = flow {
        getLocalBookDetail(isbn13)?.let { emit(it) }

        val networkBookDetail = networkDataSource.getBookDetail(isbn13)
        require(networkBookDetail.error == "0") { networkBookDetail.error }

        bookDetailDao.insertBookDetail(networkBookDetail.toBookDetailEntity())

        emit(networkBookDetail.toBookDetail())
    }

    private suspend fun getLocalBookDetail(isbn13: String): BookDetail? {
        val bookDetailEntities = bookDetailDao.getBookDetail(isbn13).firstOrNull() ?: return null
        if (bookDetailEntities.isEmpty()) return null
        return bookDetailEntities[0].toBookDetail()
    }

    private fun NetworkBookDetail.toBookDetailEntity() = BookDetailEntity(
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

    override suspend fun clear() {
        bookDao.deleteBooks()
    }

}