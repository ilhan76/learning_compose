package com.kudashov.learning_compose.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kudashov.learning_compose.network.RetrofitClient
import com.kudashov.learning_compose.network.home.HomeApi
import com.kudashov.learning_compose.network.home.HomeRepository
import com.kudashov.learning_compose.screens.home.HomeScreen
import com.kudashov.learning_compose.screens.home.HomeViewModel
import com.kudashov.learning_compose.ui.theme.LearningComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel =  HomeViewModel(
                HomeRepository(
                    RetrofitClient.getClient().create(
                        HomeApi::class.java))
            )
            LearningComposeTheme {
                HomeScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}