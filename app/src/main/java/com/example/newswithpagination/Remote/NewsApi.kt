package com.example.newswithpagination.Remote

import com.example.newswithpagination.Domain.Remote.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("apiKey") ApiKey : String = apiKey,
        @Query("country") country :String = "us",
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int = 20
    ) : News
    companion object{
        val apiKey = "b395214ecd2848489939c4609834b188"
        val url = "https://newsapi.org/v2/"
    }
}