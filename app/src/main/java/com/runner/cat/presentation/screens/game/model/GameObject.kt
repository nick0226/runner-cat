package com.runner.cat.presentation.screens.game.model

sealed class GameObject {
    data class Obstacle(var x: Float, val y: Float) : GameObject()
    data class Coin(var x: Float, val y: Float) : GameObject()
}