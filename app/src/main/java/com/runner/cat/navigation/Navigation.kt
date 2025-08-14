package com.runner.cat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.runner.cat.presentation.screens.game.GameScreen
import com.runner.cat.presentation.screens.home.HomeScaffold
import com.runner.cat.presentation.screens.rules.RulesScreen
import com.runner.cat.presentation.screens.splash.SplashScreen
import com.runner.cat.presentation.screens.statistic.StatisticScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScaffold(
                onNavigateToGame = {
                    navController.navigate(Screen.GameScreen.route)
                }
            )
        }

        composable(Screen.GameScreen.route) {
            GameScreen(
                onNavigateMainScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.RulesScreen.route) {
            RulesScreen()
        }

        composable(Screen.StatisticScreen.route) {
            StatisticScreen()
        }
    }
}