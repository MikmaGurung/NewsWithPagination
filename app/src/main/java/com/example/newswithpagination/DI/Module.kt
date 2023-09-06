package com.example.newswithpagination.DI

import com.example.newswithpagination.Dao.ArticleDao
import com.example.newswithpagination.Domain.Local.Database
import com.example.newswithpagination.Remote.NewsApi
import com.example.newswithpagination.Remote.NewsApi.Companion.url
import com.example.newswithpagination.Repository.PagintaionRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object Module {
    @Provides
    @Singleton
    fun provideRetrofit() : NewsApi =
      Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(url)
          .build()
          .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(
        database: Database,
        api: NewsApi,
    ):PagintaionRepoImpl{
        return PagintaionRepoImpl(api,database)
    }
}