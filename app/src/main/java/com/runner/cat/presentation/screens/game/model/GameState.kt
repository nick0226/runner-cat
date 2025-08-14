package com.runner.cat.presentation.screens.game.model

data class GameState(
    val cat: CatState = CatState(),
    val objects: List<GameObject> = emptyList(),
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val collectedCoins: Int = 0
)