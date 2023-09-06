package com.example.newswithpagination.Domain.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newswithpagination.Dao.ArticleDao
import com.example.newswithpagination.Dao.RemoteTableKeysDao
import com.example.newswithpagination.Domain.Remote.Article

@Database(
    [Article::class,RemoteTableKeys::class],
    version = 2)
abstract class Database : RoomDatabase() {
    abstract fun ArticleDao() : ArticleDao
    abstract fun RemoteTableKeysDao() : RemoteTableKeysDao
}