package com.jhkim.core.database.di

import android.content.Context
import androidx.room.Room
import com.jhkim.core.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideBookDatabase(
        @ApplicationContext context: Context
    ): BookDatabase = Room.databaseBuilder(
        context,
        BookDatabase::class.java,
        "book-database"
    ).build()

}