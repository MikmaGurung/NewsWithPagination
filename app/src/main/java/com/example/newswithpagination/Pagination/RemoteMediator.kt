package com.example.newswithpagination.Pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction

import com.example.newswithpagination.Domain.Local.Database
import com.example.newswithpagination.Domain.Local.RemoteTableKeys
import com.example.newswithpagination.Domain.Remote.Article
import com.example.newswithpagination.Remote.NewsApi
import java.lang.Exception

@ExperimentalPagingApi
class QuoteRemoteMediator(
    private val quoteAPI: NewsApi,
    private val quoteDatabase: Database
) : RemoteMediator<Int, Article>() {

    val quoteDao = quoteDatabase.ArticleDao()
    val quoteRemoteKeysDao = quoteDatabase.RemoteTableKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Article>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
               REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = quoteAPI.getNews(page = currentPage)
            val endOfPaginationReached = response.totalResults == currentPage

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            quoteDatabase.withTransaction {

                if (loadType == REFRESH) {
                    quoteDao.clearArticleEntity()
                    quoteRemoteKeysDao.clearRemoteTableKeys()
                }

                quoteDao.provideArticle(response.articles)
                val keys = response.articles.map { quote ->
                    RemoteTableKeys(
                        id = quote.id,
                        prevKey  = prevPage,
                        nextKey = nextPage
                    )
                }
                quoteRemoteKeysDao.provideRemoteTableKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        }
        catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteTableKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {id ->
                quoteRemoteKeysDao.getRemoteTableKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Article>
    ): RemoteTableKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteTableKeys(id = quote.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Article>
    ): RemoteTableKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteTableKeys(id = quote.id)
            }
    }
}