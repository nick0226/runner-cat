package com.runner.cat.presentation.screens.game

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runner.cat.R
import com.runner.cat.presentation.screens.game.components.GameOverDialog
import com.runner.cat.presentation.screens.game.model.GameObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    onNavigateMainScreen: () -> Unit
) {
    val viewModel: GameViewModel = koinViewModel()
    val state = viewModel.state
    val catFrames = listOf(
        R.drawable.cat_run_1,
        R.drawable.cat_run_2,
        R.drawable.cat_run_3,
        R.drawable.cat_run_4,
        R.drawable.cat_run_5,
        R.drawable.cat_run_6
    )
    val carDrawable = R.drawable.car
    val coinDrawable = R.drawable.coin

    val jumpHeight = 250f
    val catX = viewModel.run { catX }
    val catWidth = viewModel.run { catWidth }
    val catHeight = viewModel.run { catHeight }
    val obstacleWidth = viewModel.run { obstacleWidth }
    val obstacleHeight = viewModel.run { obstacleHeight }
    val coinWidth = viewModel.run { coinWidth }
    val coinHeight = viewModel.run { coinHeight }

    LaunchedEffect(Unit) {
        viewModel.dispatch(action = GameAction.StartGame)
    }

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp * configuration.densityDpi / 160f
    val infiniteTransition = rememberInfiniteTransition()

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -screenWidthPx,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    viewModel.dispatch(action = GameAction.Jump)
                })
            },
        contentAlignment = Alignment.BottomStart
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(screenWidthPx.dp)
                    .offset(x = offsetX.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.game_background),
                        contentScale = ContentScale.Crop
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(screenWidthPx.dp)
                    .offset(x = (offsetX + screenWidthPx).dp)
                    .paint(
                        painter = painterResource(id = R.drawable.game_background),
                        contentScale = ContentScale.Crop
                    )
            )
        }

        if (!state.isGameOver) {
        Image(
            painter = painterResource(id = catFrames[state.cat.frame]),
            contentDescription = null,
            modifier = Modifier
                .offset(x = viewModel.run { catX.dp }, y = (-state.cat.y - 20f).dp)
                .width(viewModel.run { catWidth.dp })
                .height(viewModel.run { catHeight.dp })
        )
            state.objects.forEach { obj ->
                when (obj) {
                    is GameObject.Obstacle -> {
                        Image(
                            painter = painterResource(id = carDrawable),
                            contentDescription = null,
                            modifier = Modifier
                                .offset(x = obj.x.dp, y = 0.dp)
                                .width(viewModel.run { obstacleWidth.dp })
                                .height(viewModel.run { obstacleHeight.dp })
                        )
                    }

                    is GameObject.Coin -> {
                        Image(
                            painter = painterResource(id = coinDrawable),
                            contentDescription = null,
                            modifier = Modifier
                                .offset(x = obj.x.dp, y = (-viewModel.run { jumpHeight }).dp)
                                .width(viewModel.run { coinWidth.dp })
                                .height(viewModel.run { coinHeight.dp })
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row {
                    Text(
                        text = "Score: ${state.score}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.score),
                        contentDescription = "Score",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row {
                    Text(
                        text = "Best: ${viewModel.bestScore}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.best_score),
                        contentDescription = "Best Score",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row {
                    Text(
                        text = "Collect coin: ${viewModel.totalCoinsCollected}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.coin_2),
                        contentDescription = "Collect coins",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        if (state.isGameOver) {
            GameOverDialog(viewModel = viewModel, onNavigateMainScreen)
        }
    }
}
