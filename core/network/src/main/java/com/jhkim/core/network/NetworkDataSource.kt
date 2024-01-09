package com.jhkim.core.network

import com.jhkim.core.network.model.NetworkBookDetail
import com.jhkim.core.network.model.NetworkSearchResult

interface NetworkDataSource {

    suspend fun searchBook(query: String, page: Int): NetworkSearchResult

    suspend fun getBookDetail(isbn: String): NetworkBookDetail

}