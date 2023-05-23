package com.kudashov.learning_compose.base.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
}

object Params {
    const val EXTRA_FIRST = "EXTRA_FIRST"
    const val EXTRA_SECOND = "EXTRA_SECOND"
    const val EXTRA_THIRD = "EXTRA_THIRD"
}