package com.jhkim.core.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSearchResult(
    @SerialName("books") val books: List<NetworkBook>,
    @SerialName("error") val error: String,
    @SerialName("page") val page: String,
    @SerialName("total") val total: String
)

@Serializable
data class NetworkBook(
    @SerialName("isbn13") val isbn13: String,
    @SerialName("image") val image: String,
    @SerialName("price") val price: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String
)