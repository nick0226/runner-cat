package com.runner.cat.presentation.screens.game

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runner.cat.presentation.screens.game.model.GameObject
import com.runner.cat.presentation.screens.game.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.content.edit
import com.runner.cat.presentation.screens.game.model.CatState

class GameViewModel(
    private val prefs: SharedPreferences
) : ViewModel() {
    var state by mutableStateOf(GameState())
        private set

    var bestScore by mutableIntStateOf(prefs.getInt("best_score", 0))
        private set

    var totalCoinsCollected by mutableIntStateOf(prefs.getInt("coins_collected", 0))
        private set

    private var gameLoopJob: Job? = null
    private var frameCounter = 0
    private val catFrameCount = 6
    private val groundY = 0f
    private val jumpHeight = 260f
    private val jumpDuration = 750L
    private val objectSpeed = 7f
    val catX = 0f
    val catWidth = 70f
    val catHeight = 70f
    val obstacleWidth = 120f
    val obstacleHeight = 120f
    val coinWidth = 80f
    val coinHeight = 80f
    private val screenWidth = 1080f

    fun dispatch(action: GameAction) {
        when (action) {
            is GameAction.StartGame -> handleStartGame()
            is GameAction.Jump -> handleJump()
            is GameAction.UpdateGame -> handleUpdateGame()
        }
    }

    private fun handleStartGame() {
        state = GameState(
            cat = CatState(),
            collectedCoins = totalCoinsCollected
        )
        frameCounter = 0
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            while (!state.isGameOver) {
                dispatch(action = GameAction.UpdateGame)
                delay(16L)
            }
        }
    }

    private fun handleJump() {
        if (!state.cat.isJumping) {
            viewModelScope.launch {
                val jumpSteps = (jumpDuration / 16L).toInt()
                for (i in 0 until jumpSteps) {
                    val t = i / jumpSteps.toFloat()
                    val y = -4 * jumpHeight * t * (t - 1)
                    state = state.copy(cat = state.cat.copy(y = y, isJumping = true))
                    delay(16L)
                }
                state = state.copy(cat = state.cat.copy(y = groundY, isJumping = false))
            }
        }
    }

    private fun handleUpdateGame() {
        frameCounter++
        val newFrame = (frameCounter / 6) % catFrameCount

        val updatedObjects = state.objects.mapNotNull { obj ->
            when (obj) {
                is GameObject.Obstacle -> {
                    val newX = obj.x - objectSpeed
                    if (newX + obstacleWidth < 0) null else obj.copy(x = newX)
                }
                is GameObject.Coin -> {
                    val newX = obj.x - objectSpeed
                    if (newX + coinWidth < 0) null else obj.copy(x = newX)
                }
            }
        }.toMutableList()

        if (frameCounter % 80 == 0) {
            updatedObjects.add(
                GameObject.Obstacle(
                    x = screenWidth,
                    y = groundY
                )
            )
        }

        if (frameCounter % 120 == 0) {
            updatedObjects.add(
                GameObject.Coin(
                    x = screenWidth,
                    y = jumpHeight
                )
            )
        }

        var score = state.score
        var isGameOver = false
        var collectedCoins = state.collectedCoins
        val catRect = Rect(catX, state.cat.y, catX + catWidth, state.cat.y + catHeight)
        val filteredObjects = updatedObjects.filterNot { obj ->
            when (obj) {
                is GameObject.Obstacle -> {
                    val obsRect = Rect(obj.x, obj.y, obj.x + obstacleWidth, obj.y + obstacleHeight)
                    if (catRect.intersects(obsRect) && state.cat.y <= groundY + 2f) {
                        isGameOver = true
                        true
                    } else false
                }
                is GameObject.Coin -> {
                    val coinRect = Rect(obj.x, obj.y, obj.x + coinWidth, obj.y + coinHeight)
                    if (catRect.intersects(coinRect)) {
                        score++
                        collectedCoins++
                        true
                    } else false
                }
            }
        }

        if (isGameOver && score > bestScore) {
            bestScore = score
            prefs.edit { putInt("best_score", bestScore) }
        }

        if (collectedCoins > totalCoinsCollected) {
            totalCoinsCollected = collectedCoins
            prefs.edit { putInt("coins_collected", totalCoinsCollected) }
        }
        state = state.copy(
            cat = state.cat.copy(frame = newFrame),
            objects = filteredObjects,
            score = score,
            isGameOver = isGameOver,
            collectedCoins = collectedCoins
        )
    }

    data class Rect(val left: Float, val top: Float, val right: Float, val bottom: Float) {
        fun intersects(other: Rect): Boolean {
            return left < other.right && right > other.left &&
                    top < other.bottom && bottom > other.top
        }
    }
}