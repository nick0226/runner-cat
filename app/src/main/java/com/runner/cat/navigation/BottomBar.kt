package com.runner.cat.navigation

import androidx.annotation.DrawableRes
import com.runner.cat.R

sealed class BottomBar(
    val route: String,
    @DrawableRes val iconRes: Int
) {
    data object MainScreen : BottomBar(
        route = Screen.MainScreen.route,
        iconRes = R.drawable.home
    )

    data object RulesScreen : BottomBar(
        route = Screen.RulesScreen.route,
        iconRes = R.drawable.rules
    )

    data object StatisticScreen : BottomBar(
        route = Screen.StatisticScreen.route,
        iconRes = R.drawable.statistic
    )
}