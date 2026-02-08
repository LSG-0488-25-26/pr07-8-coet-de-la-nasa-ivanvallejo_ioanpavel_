package com.example.jikan.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.jikan.viewModel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    viewModel: AnimeViewModel,
    onAnimeClick: (Int) -> Unit
) {
    val animeList by viewModel.animeList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val searchText by viewModel.searchText.observeAsState("")
    val error by viewModel.error.observeAsState()

    val config = LocalConfiguration.current
    val columns = when {
        config.screenWidthDp >= 600 -> 4 // Tablet
        else -> 2 // Móvil
    }

    val filteredList = animeList.filter {
        it.title.contains(searchText, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.loadTopAnimes()
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Jikan Anime") })
                // REQUISITO: SEARCHBAR
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.onSearchTextChange(it) },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    placeholder = { Text("Buscar...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )
            }
        },
        bottomBar = {
            // REQUISITO: BOTTOMBAR
            BottomAppBar {
                Text(modifier = Modifier.padding(16.dp), text = "Resultados: ${filteredList.size}")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadTopAnimes() }) {
                            Text("Reintentar")
                        }
                    }
                }
                animeList.isEmpty() -> {
                    Text(
                        text = "No hay animes disponibles",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredList) { anime ->
                            AnimeGridItem(
                                anime = anime,
                                onClick = { onAnimeClick(anime.malId) }
                            )
                        }
                    }
                }
            }
        }
    }
}