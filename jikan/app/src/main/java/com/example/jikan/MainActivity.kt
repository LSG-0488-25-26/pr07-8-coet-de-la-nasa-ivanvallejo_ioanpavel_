package com.example.jikan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.jikan.ApiInterface.ApiInterface
import com.example.jikan.Data.AppDatabase
import com.example.jikan.navigation.AnimeNavigation
import com.example.jikan.repository.AnimeRepository
import com.example.jikan.ui.theme.JikanTheme
import com.example.jikan.viewModel.AnimeViewModel
import com.example.jikan.viewModel.AnimeViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: AnimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializar la Base de Datos de Room
        val database = AppDatabase.getDatabase(this)
        val animeDao = database.animeDao()

        // 2. Inicializar la API
        val api = ApiInterface.create()

        // 3. Inicializar el Repositorio pasándole AMBOS (API y DAO)
        // Nota: Asegúrate de que el constructor de AnimeRepository acepte el DAO ahora
        val repository = AnimeRepository(api, animeDao)

        // 4. Configurar el Factory y el ViewModel
        val factory = AnimeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AnimeViewModel::class.java]

        setContent {
            JikanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimeNavigation(viewModel = viewModel)
                }
            }
        }
    }
}