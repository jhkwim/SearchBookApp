package com.jhkim.core.network.di

import com.jhkim.core.network.NetworkDataSource
import com.jhkim.core.network.retrofit.RetrofitBookApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    internal abstract fun bindNetworkDataSource(
        retrofitBookApiService: RetrofitBookApiService
    ): NetworkDataSource

}