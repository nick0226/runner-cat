package com.runner.cat.navigation

sealed class Screen (val route: String) {
    data object SplashScreen: Screen ("splash_screen")
    data object MainScreen: Screen ("main_screen")
    data object HomeScreen : Screen("home_screen")
    data object GameScreen: Screen ("game_screen")
    data object RulesScreen: Screen ("rules_screen")
    data object StatisticScreen: Screen ("statistic_screen")
}
