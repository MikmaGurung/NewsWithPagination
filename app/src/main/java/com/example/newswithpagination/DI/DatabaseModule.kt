package com.example.newswithpagination.DI

import android.content.Context
import androidx.room.Room
import com.example.newswithpagination.Domain.Local.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase (@ApplicationContext context: Context) : Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "NewsDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}