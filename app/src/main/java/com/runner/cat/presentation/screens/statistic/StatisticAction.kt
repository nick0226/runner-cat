package com.runner.cat.presentation.screens.statistic

sealed interface StatisticAction {

    data object RefreshStats : StatisticAction

}