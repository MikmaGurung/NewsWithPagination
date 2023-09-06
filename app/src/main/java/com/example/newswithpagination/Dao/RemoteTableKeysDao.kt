package com.example.newswithpagination.Dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newswithpagination.Domain.Local.RemoteTableKeys

@Dao
interface RemoteTableKeysDao{
    @Insert
    suspend fun provideRemoteTableKeys (tableKeys : List<RemoteTableKeys>)

    @Query("SELECT * FROM RemoteTableKeys WHERE id = :id")
    suspend fun getRemoteTableKeys(id : Int?) : RemoteTableKeys

    @Query("DELETE FROM RemoteTableKeys")
    suspend fun clearRemoteTableKeys ()
}