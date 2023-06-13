package com.kudashov.learning_compose.base.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kudashov.learning_compose.screens.detail.PhotoDetailRoute
import com.kudashov.learning_compose.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
        composable(
            route = "${Screen.Detail.route}/{photoId}",
            arguments = listOf(navArgument("photoId") { type = NavType.StringType })
        ) {
            it.arguments?.getString("photoId")?.let { id ->
                PhotoDetailRoute(
                    photoId = id,
                    navController = navController
                )
            }
        }
    }
}