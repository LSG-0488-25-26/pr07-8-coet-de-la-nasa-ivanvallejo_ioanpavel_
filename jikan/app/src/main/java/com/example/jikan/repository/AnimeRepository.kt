package com.example.jikan.repository

import android.util.Log
import com.example.jikan.ApiInterface.ApiInterface
import com.example.jikan.Model.AnimeData
class AnimeRepository(private val api: ApiInterface) {

    suspend fun getTopAnimes(): List<AnimeData>? {
        return try {
            Log.d("AnimeRepository", "Fetching top animes...")

            // Obtener las primeras 2 páginas para tener 50 animes
            val page1 = api.getTopAnime(page = 1, limit = 25)
            val page2 = api.getTopAnime(page = 2, limit = 25)

            Log.d("AnimeRepository", "Page 1 code: ${page1.code()}")
            Log.d("AnimeRepository", "Page 2 code: ${page2.code()}")

            val allAnimes = mutableListOf<AnimeData>()

            if (page1.isSuccessful) {
                page1.body()?.data?.let { allAnimes.addAll(it) }
            }

            if (page2.isSuccessful) {
                page2.body()?.data?.let { allAnimes.addAll(it) }
            }

            Log.d("AnimeRepository", "Success! Got ${allAnimes.size} animes total")

            if (allAnimes.isEmpty()) {
                Log.e("AnimeRepository", "No animes received")
                null
            } else {
                allAnimes
            }

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