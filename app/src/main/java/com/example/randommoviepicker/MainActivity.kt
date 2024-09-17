package com.example.randommoviepicker
import com.example.randommoviepicker.viewmodel.MovieViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.randommoviepicker.ui.theme.RandomMoviePickerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randommoviepicker.ui.theme.MovieDetailScreen
import com.example.randommoviepicker.ui.theme.MovieListScreen
import com.example.randommoviepicker.ui.theme.SavedMoviesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MovieViewModel = viewModel()

            RandomMoviePickerTheme {
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: MovieViewModel) {
    NavHost(navController = navController, startDestination = "movie_list") {
        composable("movie_list") {
            MovieListScreen(navController = navController, viewModel = viewModel)
        }
        composable("movie_detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            MovieDetailScreen(navController = navController, viewModel = viewModel, movieId = movieId)
        }
        composable("savedMoviesScreen") {
            SavedMoviesScreen(viewModel = viewModel)
        }
    }
}
