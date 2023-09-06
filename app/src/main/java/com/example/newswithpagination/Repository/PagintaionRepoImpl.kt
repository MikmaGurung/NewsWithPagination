package com.example.newswithpagination.Repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newswithpagination.Dao.ArticleDao
import com.example.newswithpagination.Domain.Local.Database
import com.example.newswithpagination.Pagination.QuoteRemoteMediator
import com.example.newswithpagination.Remote.NewsApi
import javax.inject.Inject

class PagintaionRepoImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val database: Database,

) :PagintaionRepository{
    @OptIn(ExperimentalPagingApi::class)
    override fun  getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = QuoteRemoteMediator(newsApi,database),
        pagingSourceFactory = {database.ArticleDao().getArticle()}
    ).flow
}