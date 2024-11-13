package com.bae.composeflappy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bae.composeflappy.ui.components.Pipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameManagerViewModel : ViewModel() {
    enum class GameState { START, RUNNING, GAME_OVER }

    private val _gameState = MutableStateFlow(GameState.START)
    val gameState = _gameState.asStateFlow()

    private val _birdY = MutableStateFlow(400f)
    val birdY = _birdY.asStateFlow()

    private val _pipes = MutableStateFlow(listOf<Pipe>())
    val pipes = _pipes.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private var gameTimer: Job? = null
    private var birdVelocity = 0f

    init {
        resetGame()
    }

    fun startGame() {
        _gameState.value = GameState.RUNNING
        resetGame()

        gameTimer = viewModelScope.launch {
            while (_gameState.value == GameState.RUNNING) {
                delay(16L)
                updateBird()
                updatePipes()
                checkCollision()
            }
        }
    }

    fun restartGame() {
        _gameState.value = GameState.START
        resetGame()
    }

    private fun resetGame() {
        _birdY.value = 400f
        _score.value = 0
        _pipes.value = generateInitialPipes()
        birdVelocity = 0f
    }

    private fun updateBird() {
        birdVelocity += 0.5f // gravity
        _birdY.value += birdVelocity
    }

    private fun updatePipes() {
        _pipes.value = _pipes.value.map { pipe ->
            pipe.copy(x = pipe.x - 5f)
        }.filter { pipe -> pipe.x > -100f }

        if (_pipes.value.isEmpty() || _pipes.value.last().x < 500f) {
            _pipes.value += generateNewPipe()
        }
    }

    private fun checkCollision() {
        val birdY = _birdY.value
        val pipes = _pipes.value

        // game over condition
        if (birdY < -100f || birdY > 800f) {
            endGame()
            return
        }

        // calculate pipe event
        _pipes.value = pipes.map { pipe ->
            if (pipe.x in 0f..80f) {
                if (birdY < pipe.gapY - 100f || birdY > pipe.gapY + 100f) {
                    endGame()
                    pipe
                } else if (!pipe.passed) {
                    // add score
                    _score.value += 1
                    pipe.copy(passed = true)
                } else {
                    pipe
                }
            } else {
                pipe
            }
        }
    }

    private fun endGame() {
        _gameState.value = GameState.GAME_OVER
        gameTimer?.cancel()
    }

    fun onTap() {
        if (_gameState.value == GameState.RUNNING) {
            birdVelocity = -10f // 새를 위로 점프
        }
    }

    private fun generateInitialPipes(): List<Pipe> {
        return List(3) { generateNewPipe(xOffset = 800f + it * 600f) }
    }

    private fun generateNewPipe(xOffset: Float = 1200f): Pipe {
        val gapY = (200..600).random().toFloat()
        return Pipe(x = xOffset, gapY = gapY)
    }
}