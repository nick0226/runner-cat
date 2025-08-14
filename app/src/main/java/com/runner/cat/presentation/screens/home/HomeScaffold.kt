package com.runner.cat.presentation.screens.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.runner.cat.navigation.BottomBar
import com.runner.cat.presentation.screens.main.MainScreen
import com.runner.cat.presentation.screens.rules.RulesScreen
import com.runner.cat.presentation.screens.statistic.StatisticScreen

@Composable
fun HomeScaffold(
    onNavigateToGame: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val screens = listOf(
                BottomBar.MainScreen,
                BottomBar.RulesScreen,
                BottomBar.StatisticScreen,
            )
            NavigationBar (
                modifier = Modifier.height(42.dp)
            ) {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomBar.MainScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomBar.MainScreen.route) {
                MainScreen( onNavigateToGame = onNavigateToGame )
            }
            composable(BottomBar.RulesScreen.route) {
                RulesScreen()
            }
            composable(BottomBar.StatisticScreen.route) {
                StatisticScreen()
            }
        }
    }
}