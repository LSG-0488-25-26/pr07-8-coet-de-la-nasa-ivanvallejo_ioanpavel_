package com.example.jikan.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikan.Model.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimes(animes: List<AnimeEntity>)

    @Query("SELECT * FROM anime_table")
    fun getAllAnimes(): Flow<List<AnimeEntity>>

    @Query("DELETE FROM anime_table")
    suspend fun deleteAll()
}