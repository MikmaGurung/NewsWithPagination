package com.example.newswithpagination.Repository

import androidx.paging.PagingData
import com.example.newswithpagination.Domain.Remote.Article
import kotlinx.coroutines.flow.Flow

interface PagintaionRepository { fun getQuotes() : Flow<PagingData<Article>>
}