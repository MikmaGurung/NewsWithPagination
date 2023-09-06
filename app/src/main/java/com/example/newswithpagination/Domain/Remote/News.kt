package com.example.newswithpagination.Domain.Remote

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)