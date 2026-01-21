package com.example.jikan.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jikan.View.AnimeDetailScreen
import com.example.jikan.View.AnimeListScreen
import com.example.jikan.viewModel.AnimeViewModel


@Composable
fun AnimeNavigation(viewModel: AnimeViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "anime_list"
    ) {
        composable("anime_list") {
            AnimeListScreen(
                viewModel = viewModel,
                onAnimeClick = { animeId ->
                    navController.navigate("anime_detail/$animeId")
                }
            )
        }

        composable(
            route = "anime_detail/{animeId}",
            arguments = listOf(
                navArgument("animeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            AnimeDetailScreen(
                viewModel = viewModel,
                animeId = animeId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}