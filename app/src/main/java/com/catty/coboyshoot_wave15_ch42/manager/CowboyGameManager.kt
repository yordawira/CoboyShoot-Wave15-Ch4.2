package com.catty.coboyshoot_wave15_ch42.manager

import com.catty.coboyshoot_wave15_ch42.R
import com.catty.coboyshoot_wave15_ch42.enum.GameState
import com.catty.coboyshoot_wave15_ch42.enum.PlayerPosition
import com.catty.coboyshoot_wave15_ch42.enum.PlayerSide
import com.catty.coboyshoot_wave15_ch42.enum.PlayerState
import com.catty.coboyshoot_wave15_ch42.model.Player
import kotlin.random.Random

interface CowboyGameManager {
    fun initGame()
    fun movePlayerToTop()
    fun movePlayerToBottom()
    fun startOrRestartGame()
}

interface CowboyGameListener {
    fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int)
    fun onGameStateChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner: Player)
}

class ComputerEnemyCowboyGameManager(
    private val listener: CowboyGameListener
) : CowboyGameManager {

    private lateinit var playerOne: Player

    private lateinit var playerTwo: Player

    private lateinit var gameState: GameState

    override fun initGame() {
        setGameState(GameState.IDLE)
        playerOne = Player(PlayerSide.PLAYER_ONE, PlayerState.IDLE, PlayerPosition.MIDDLE)
        playerTwo = Player(PlayerSide.PLAYER_TWO, PlayerState.IDLE, PlayerPosition.MIDDLE)
        notifyPlayerDataChanged()
        setGameState(GameState.STARTED)
    }

    private fun notifyPlayerDataChanged() {
        listener.onPlayerStatusChanged(
            playerOne,
            getPlayerOneDrawableByState(playerOne.playerState)
        )
        listener.onPlayerStatusChanged(
            playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerState)
        )
    }

    override fun movePlayerToTop() {
        if (gameState != GameState.FINISHED &&
            playerOne.playerPosition.ordinal > PlayerPosition.TOP.ordinal
        ) {
            val currentIndex = playerOne.playerPosition.ordinal
            setPlayerOneMovement(getPlayerPositionByOrdinal(currentIndex - 1), PlayerState.IDLE)
        }
    }

    override fun movePlayerToBottom() {
        if (gameState != GameState.FINISHED &&
            playerOne.playerPosition.ordinal < PlayerPosition.BOTTOM.ordinal
        ) {
            val currentIndex = playerOne.playerPosition.ordinal
            setPlayerOneMovement(getPlayerPositionByOrdinal(currentIndex + 1), PlayerState.IDLE)
        }
    }


    private fun setPlayerOneMovement(
        playerPosition: PlayerPosition = playerOne.playerPosition,
        playerState: PlayerState = playerOne.playerState
    ) {
        playerOne.apply {
            this.playerPosition = playerPosition
            this.playerState = playerState
        }
        listener.onPlayerStatusChanged(
            playerOne,
            getPlayerOneDrawableByState(playerOne.playerState)
        )
    }

    private fun setPlayerTwoMovement(
        playerPosition: PlayerPosition = playerTwo.playerPosition,
        playerState: PlayerState = playerTwo.playerState
    ) {
        playerTwo.apply {
            this.playerPosition = playerPosition
            this.playerState = playerState
        }
        listener.onPlayerStatusChanged(
            playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerState)
        )
    }

    private fun getPlayerOneDrawableByState(playerState: PlayerState): Int {
        return when (playerState) {
            PlayerState.IDLE -> R.drawable.ic_cowboy_left_shoot_false
            PlayerState.SHOOT -> R.drawable.ic_cowboy_left_shoot_true
            PlayerState.DEAD -> R.drawable.ic_cowboy_left_dead
        }
    }

    private fun getPlayerTwoDrawableByState(playerState: PlayerState): Int {
        return when (playerState) {
            PlayerState.IDLE -> R.drawable.ic_cowboy_right_shoot_false
            PlayerState.SHOOT -> R.drawable.ic_cowboy_right_shoot_true
            PlayerState.DEAD -> R.drawable.ic_cowboy_right_dead
        }
    }

    private fun getPlayerPositionByOrdinal(index: Int): PlayerPosition {
        return PlayerPosition.values()[index]
    }

    private fun setGameState(newGameState: GameState) {
        gameState = newGameState
        listener.onGameStateChanged(gameState)
    }

    private fun startGame() {
        playerTwo.apply {
            playerPosition = getPlayerTwoPosition()
        }
        checkPlayerWinner()
    }

    private fun checkPlayerWinner() {
        val winner = if (playerOne.playerPosition == playerTwo.playerPosition) {
            setPlayerOneMovement(playerState = PlayerState.DEAD)
            setPlayerTwoMovement(playerState = PlayerState.SHOOT)
            playerOne
        } else {
            setPlayerOneMovement(playerState = PlayerState.SHOOT)
            setPlayerTwoMovement(playerState = PlayerState.DEAD)
            playerTwo
        }
        setGameState(GameState.FINISHED)
        listener.onGameFinished(gameState, winner)
    }

    private fun resetGame() {
        initGame()
    }

    private fun getPlayerTwoPosition(): PlayerPosition {
        val randomPosition = Random.nextInt(0, until = PlayerPosition.values().size)
        return getPlayerPositionByOrdinal(randomPosition)
    }

    override fun startOrRestartGame() {
        if (gameState != GameState.FINISHED) {
            startGame()
        } else {
            resetGame()
        }
    }
}