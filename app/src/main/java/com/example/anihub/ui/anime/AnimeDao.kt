package com.example.anihub.ui.anime

import androidx.lifecycle.LiveData
import androidx.room.*
import api.BrowseAnimeQuery
import com.apollographql.apollo.api.Response

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    suspend fun getAll(): List<AnimeModel>

    @Query("SELECT * FROM anime where id LIKE :id")
    suspend fun getById(id: Int): AnimeModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: AnimeModel)

    @Insert
    suspend fun insertAll(vararg models: AnimeModel)

    @Delete
    suspend fun delete(model: AnimeModel)

    @Update
    suspend fun updateModel(model: AnimeModel)
}