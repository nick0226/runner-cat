package com.runner.cat.di

import android.content.Context
import android.content.SharedPreferences
import com.runner.cat.presentation.screens.game.GameViewModel
import com.runner.cat.presentation.screens.statistic.StatisticViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    }

    viewModel {
        GameViewModel(get())
    }

    viewModel {
        StatisticViewModel(get())
    }

}