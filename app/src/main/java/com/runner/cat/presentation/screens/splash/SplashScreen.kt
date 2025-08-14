package com.runner.cat.presentation.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runner.cat.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val images = listOf(
        R.drawable.cat_run_1,
        R.drawable.cat_run_2,
        R.drawable.cat_run_3,
        R.drawable.cat_run_4,
        R.drawable.cat_run_5,
        R.drawable.cat_run_6,
    )

    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000),
        label = "imageAlpha"
    )

    LaunchedEffect(Unit) {
        repeat(30) {
            delay(100)
            currentIndex = (currentIndex + 1) % images.size
        }
        onNavigateToMain()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.city_background),
                contentScale = ContentScale.Crop
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = images[currentIndex]),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .alpha(alpha)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Runner Cat",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}