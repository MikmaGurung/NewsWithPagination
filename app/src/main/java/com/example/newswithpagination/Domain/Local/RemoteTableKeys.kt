package com.example.newswithpagination.Domain.Local

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class RemoteTableKeys (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val prevKey : Int?,
    val nextKey : Int?
)