package com.kudashov.learning_compose.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.kudashov.learning_compose.screens.home.HomeScreen
import com.kudashov.learning_compose.screens.home.HomeViewModel
import com.kudashov.learning_compose.ui.theme.LearningComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel =  hiltViewModel<HomeViewModel>()
            LearningComposeTheme {
                HomeScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}