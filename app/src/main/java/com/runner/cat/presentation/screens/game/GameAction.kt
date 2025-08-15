package com.runner.cat.presentation.screens.game

sealed interface GameAction {

    data object StartGame : GameAction

    data object Jump : GameAction

    data object UpdateGame : GameAction

}