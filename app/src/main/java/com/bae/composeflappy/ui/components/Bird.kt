package com.bae.composeflappy.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bae.composeflappy.R
import com.bae.composeflappy.ui.theme.ComposeFlappyTheme

@Composable
fun Bird(birdY: Float) {
    val imageResources = listOf(
        painterResource(id = R.drawable.blue_bird_downflap),
        painterResource(id = R.drawable.blue_bird_midflap),
        painterResource(id = R.drawable.blue_bird_upflap),
        painterResource(id = R.drawable.blue_bird_midflap)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "flap_animation")
    val currentIndex by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = imageResources.size,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flap_animation"
    )

    Image(
        painter = imageResources[currentIndex],
        contentDescription = "Bird",
        modifier = Modifier
            .size(50.dp)
            .offset(y = birdY.dp)
    )
}

@Preview
@Composable
private fun BirdPreview() {
    ComposeFlappyTheme {
        Bird(birdY = 0f)
    }
}