package com.jhkim.core.network.retrofit

import com.jhkim.core.network.NetworkDataSource
import com.jhkim.core.network.model.NetworkBookDetail
import com.jhkim.core.network.model.NetworkSearchResult
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

internal interface RetrofitBookApi {

    @GET("search/{query}/{page}")
    suspend fun search(
        @Path("query", encoded = true) query: String,
        @Path("page") page: Int
    ): NetworkSearchResult

    @GET("books/{isbn13}")
    suspend fun getDetail(
        @Path("isbn13") isbn: String
    ): NetworkBookDetail

}

@Singleton
class RetrofitBookApiService @Inject constructor(
    retrofit: Retrofit
): NetworkDataSource {

    private val networkApi = retrofit.create(RetrofitBookApi::class.java)

    override suspend fun searchBook(query: String, page: Int) = networkApi.search(query, page)

    override suspend fun getBookDetail(isbn: String) = networkApi.getDetail(isbn)

}