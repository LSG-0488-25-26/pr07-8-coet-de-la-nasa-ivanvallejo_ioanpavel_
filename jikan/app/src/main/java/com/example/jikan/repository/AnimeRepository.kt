package com.example.jikan.repository

import android.util.Log
import com.example.jikan.ApiInterface.ApiInterface
import com.example.jikan.Data.AnimeDao
import com.example.jikan.Model.AnimeData
import com.example.jikan.Model.AnimeEntity

class AnimeRepository(private val api: ApiInterface, private val dao: AnimeDao) {

    suspend fun getTopAnimes(): List<AnimeData>? {
        return try {
            val response1 = api.getTopAnime(page = 1)
            val response2 = api.getTopAnime(page = 2)

            val allAnimes = mutableListOf<AnimeData>()
            response1.body()?.data?.let { allAnimes.addAll(it) }
            response2.body()?.data?.let { allAnimes.addAll(it) }

            if (allAnimes.isNotEmpty()) {
                // Mapear de AnimeData (API) a AnimeEntity (Room)
                val entities = allAnimes.map {
                    AnimeEntity(it.malId, it.title, it.images?.jpg?.imageUrl ?: "", it.score ?: 0.0)
                }
                dao.deleteAll()
                dao.insertAnimes(entities)
            }
            allAnimes
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Exception fetching animes", e)
            e.printStackTrace()
            null
        }
    }

    suspend fun getAnimeById(animeId: Int): AnimeData? {
        return try {
            Log.d("AnimeRepository", "Fetching anime with ID: $animeId")
            val response = api.getAnimeById(animeId)

            Log.d("AnimeRepository", "Response code: ${response.code()}")

            if (response.isSuccessful) {
                val data = response.body()?.data
                Log.d("AnimeRepository", "Success! Got anime: ${data?.title}")
                data
            } else {
                Log.e("AnimeRepository", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Exception fetching anime details", e)
            e.printStackTrace()
            null
        }
    }
}