package com.bae.composeflappy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bae.composeflappy.GameManagerViewModel.GameState
import com.bae.composeflappy.ui.components.Bird
import com.bae.composeflappy.ui.components.GameOver
import com.bae.composeflappy.ui.components.GameStart
import com.bae.composeflappy.ui.components.Pipe
import com.bae.composeflappy.ui.theme.ComposeFlappyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeFlappyTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    FlappyBirdGame(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FlappyBirdGame(
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { GameManagerViewModel() }

    val gameState by viewModel.gameState.collectAsStateWithLifecycle()
    val birdY by viewModel.birdY.collectAsStateWithLifecycle()
    val pipes by viewModel.pipes.collectAsStateWithLifecycle()
    val score by viewModel.score.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        // Background
        Image(
            painter = painterResource(R.drawable.flappy_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        when (gameState) {
            GameState.START -> {
                GameStart(
                    onStart = { viewModel.startGame() }
                )
            }
            GameState.RUNNING -> {
                Bird(birdY = birdY)
                pipes.forEach { pipe ->
                    Pipe(pipe = pipe)
                }
                Text(
                    text = "Score: $score",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { viewModel.onTap() }
                )
            }
            GameState.GAME_OVER -> {
                GameOver(onRestart = { viewModel.restartGame() })
            }
        }
    }
}

@Preview
@Composable
private fun FlappyBirdGamePreview() {
    ComposeFlappyTheme {
        FlappyBirdGame()
    }
}