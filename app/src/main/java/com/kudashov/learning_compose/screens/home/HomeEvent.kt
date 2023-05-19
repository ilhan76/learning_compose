package com.kudashov.learning_compose.screens.home

sealed interface HomeEvent {
    object LoadPhotos : HomeEvent
}