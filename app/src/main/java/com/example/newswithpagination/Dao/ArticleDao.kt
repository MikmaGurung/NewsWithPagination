package com.example.newswithpagination.Dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newswithpagination.Domain.Remote.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun provideArticle (article : List<Article>)

    @Query("SELECT * FROM Article")
    fun getArticle() : PagingSource<Int,Article>

    @Query("DELETE FROM Article")
    fun clearArticleEntity ()

}