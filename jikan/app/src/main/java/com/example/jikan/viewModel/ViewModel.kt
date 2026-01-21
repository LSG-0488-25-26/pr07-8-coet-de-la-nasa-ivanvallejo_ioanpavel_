package com.example.jikan.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jikan.Model.AnimeData
import com.example.jikan.repository.AnimeRepository
import kotlinx.coroutines.launch


class AnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _animeList = MutableLiveData<List<AnimeData>>()
    val animeList: LiveData<List<AnimeData>> = _animeList

    private val _selectedAnime = MutableLiveData<AnimeData>()
    val selectedAnime: LiveData<AnimeData> = _selectedAnime

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadTopAnimes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val animes = repository.getTopAnimes()
            if (animes != null) {
                _animeList.value = animes
            } else {
                _error.value = "Error al cargar los animes"
            }

            _isLoading.value = false
        }
    }

    fun loadAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val anime = repository.getAnimeById(animeId)
            if (anime != null) {
                _selectedAnime.value = anime
            } else {
                _error.value = "Error al cargar los detalles"
            }

            _isLoading.value = false
        }
    }
}

class AnimeViewModelFactory(
    private val repository: AnimeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}