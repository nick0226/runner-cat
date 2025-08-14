package com.runner.cat.presentation.screens.game.model

data class CatState(
    val y: Float = 0f,
    val isJumping: Boolean = false,
    val frame: Int = 0
)