package com.example.newswithpagination.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newswithpagination.Repository.PagintaionRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
   repoImpl: PagintaionRepoImpl
) :ViewModel() {
    val _list = repoImpl.getQuotes().cachedIn(viewModelScope)

}