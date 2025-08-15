package com.runner.cat.presentation.screens.statistic

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.runner.cat.presentation.screens.statistic.model.StatisticState

class StatisticViewModel(
    private val prefs: SharedPreferences
) : ViewModel() {
    var state by mutableStateOf(StatisticState())
        private set

    init {
        dispatch(action = StatisticAction.RefreshStats)
    }

    fun dispatch(action: StatisticAction) {
        when (action) {
            StatisticAction.RefreshStats -> handleRefreshData()
        }
    }

    private fun handleRefreshData() {
        state = StatisticState(
            score = prefs.getInt("best_score", 0),
            collectedCoins = prefs.getInt("coins_collected", 0)
        )
    }
}

