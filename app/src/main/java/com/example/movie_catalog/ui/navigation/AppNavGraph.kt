package com.example.movie_catalog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movie_catalog.ui.detail.DetailScreen
import com.example.movie_catalog.ui.genre.GenreScreen
import com.example.movie_catalog.ui.movie.MovieListScreen


@Composable
fun AppNavGraph() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = GenreRoute) {
        composable<GenreRoute> {
            GenreScreen(
                onGenreClick = { genre ->
                    navController.navigate(MovieListRoute(genre.id, genre.name))
                }
            )
        }

        composable<MovieListRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MovieListRoute>()
            MovieListScreen(
                genreName = route.genreName,
                onBack = { navController.popBackStack() },
                onMovieClick = { movie -> navController.navigate(DetailRoute(movie.id)) }
            )

        }

        composable<DetailRoute> {
            DetailScreen(
                onBack = { navController.popBackStack() },
                onReviewsClick = { movieId -> navController.navigate(ReviewRoute(movieId)) }
            )
        }
    }
}