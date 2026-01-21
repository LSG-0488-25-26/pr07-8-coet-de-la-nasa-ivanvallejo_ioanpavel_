package com.example.jikan.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jikan.Model.AnimeData

@Composable
fun AnimeDetailContent(anime: AnimeData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Imagen principal
        AsyncImage(
            model = anime.images?.jpg?.imageUrl ?: anime.images?.jpg?.imageUrl,
            contentDescription = anime.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = anime.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Título en inglés
            anime.titleEnglish?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Título en japonés
            anime.titleJapanese?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Rating y info básica
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(
                    label = "Rating",
                    value = "⭐ ${anime.score ?: "N/A"}"
                )
                InfoChip(
                    label = "Rank",
                    value = "#${anime.rank ?: "N/A"}"
                )
                InfoChip(
                    label = "Popularidad",
                    value = "#${anime.popularity ?: "N/A"}"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información adicional
            InfoRow(label = "Tipo", value = anime.type ?: "N/A")
            InfoRow(label = "Episodios", value = anime.episodes?.toString() ?: "N/A")
            InfoRow(label = "Estado", value = anime.status ?: "N/A")
            InfoRow(
                label = "Año",
                value = "${anime.season?.capitalize() ?: ""} ${anime.year ?: ""}".trim()
            )

            // Géneros
            anime.genres?.takeIf { it.isNotEmpty() }?.let { genres ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Géneros:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = genres.joinToString(", ") { it.name },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Estudios
            anime.studios?.takeIf { it.isNotEmpty() }?.let { studios ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Estudios:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = studios.joinToString(", ") { it.name },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Sinopsis
            anime.synopsis?.let { synopsis ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sinopsis:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )
            }
        }
    }
}
