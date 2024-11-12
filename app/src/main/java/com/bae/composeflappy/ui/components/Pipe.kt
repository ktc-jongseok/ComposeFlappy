package com.bae.composeflappy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bae.composeflappy.R
import com.bae.composeflappy.ui.theme.ComposeFlappyTheme

@Composable
fun Pipe(
    pipe: Pipe,
    pipeWidth: Dp = 80.dp,
    gapHeight: Dp = 200.dp
) {
    // Top Pipe
    Image(
        painter = painterResource(R.drawable.pipe_top),
        contentDescription = "Top Pipe",
        modifier = Modifier
            .offset(
                x = pipe.x.dp,
                y = -(pipe.gapY.dp - (gapHeight / 2))
            )
            .width(pipeWidth),
        contentScale = ContentScale.FillWidth
    )

    // Bottom Pipe
    Image(
        painter = painterResource(R.drawable.pipe_bottom),
        contentDescription = "Bottom Pipe",
        modifier = Modifier
            .offset(
                x = pipe.x.dp,
                y = (pipe.gapY.dp + (gapHeight / 2))
            )
            .width(pipeWidth),
        contentScale = ContentScale.FillWidth
    )
}

data class Pipe(
    val x: Float,
    val gapY: Float // Score Area
)

@Preview
@Composable
private fun PipePreview() {
    ComposeFlappyTheme {
        Pipe(
            pipe = Pipe(
                x = 0f,
                gapY = 300f
            )
        )
    }
}