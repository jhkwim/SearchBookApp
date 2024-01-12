package com.jhkim.core.database.di

import com.jhkim.core.database.BookDatabase
import com.jhkim.core.database.dao.BookDao
import com.jhkim.core.database.dao.BookDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideBookDao(database: BookDatabase): BookDao = database.bookDao()

    @Provides
    fun provideBookDetailDao(database: BookDatabase): BookDetailDao = database.bookDetailDao()

}