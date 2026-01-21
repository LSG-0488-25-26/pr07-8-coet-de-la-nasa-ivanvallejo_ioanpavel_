package com.example.jikan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.jikan.ApiInterface.ApiInterface
import com.example.jikan.navigation.AnimeNavigation
import com.example.jikan.repository.AnimeRepository
import com.example.jikan.ui.theme.JikanTheme
import com.example.jikan.viewModel.AnimeViewModel
import com.example.jikan.viewModel.AnimeViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: AnimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        val api = ApiInterface.create()
        val repository = AnimeRepository(api)
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