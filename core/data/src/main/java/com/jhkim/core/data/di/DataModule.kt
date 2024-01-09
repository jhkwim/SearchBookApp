package com.jhkim.core.data.di

import com.jhkim.core.data.repository.BookRepository
import com.jhkim.core.data.repository.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

}