package com.example.newswithpagination.Presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.newswithpagination.Domain.Remote.Article

@Composable
fun AppScreen(article : LazyPagingItems<Article>){
    LazyColumn(modifier = Modifier.fillMaxSize() ){
        items(article){ article ->
            if (article != null) {
                AppList(article = article)
            }

        }
    }
}