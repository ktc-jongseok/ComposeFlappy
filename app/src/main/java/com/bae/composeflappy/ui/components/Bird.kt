package com.bae.composeflappy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bae.composeflappy.R
import com.bae.composeflappy.ui.theme.ComposeFlappyTheme

@Composable
fun Bird(birdY: Float) {
    Image(
        painter = painterResource(R.drawable.blue_bird),
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