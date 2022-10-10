package com.catty.coboyshoot_wave15_ch42.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.catty.coboyshoot_wave15_ch42.R
import com.catty.coboyshoot_wave15_ch42.databinding.ActivityGameBinding
import com.catty.coboyshoot_wave15_ch42.enum.GameState
import com.catty.coboyshoot_wave15_ch42.enum.PlayerPosition
import com.catty.coboyshoot_wave15_ch42.enum.PlayerSide
import com.catty.coboyshoot_wave15_ch42.manager.ComputerEnemyCowboyGameManager
import com.catty.coboyshoot_wave15_ch42.manager.CowboyGameListener
import com.catty.coboyshoot_wave15_ch42.manager.CowboyGameManager
import com.catty.coboyshoot_wave15_ch42.model.Player

class GameActivity : AppCompatActivity(), CowboyGameListener {
    private val binding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val cowboyGameManager: CowboyGameManager by lazy {
        ComputerEnemyCowboyGameManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cowboyGameManager.initGame()
        setOnClickListeners()
        supportActionBar?.hide()
    }

    private fun setOnClickListeners() {
        binding.ivArrowUp.setOnClickListener {
            cowboyGameManager.movePlayerToTop()
        }
        binding.ivArrowDown.setOnClickListener {
            cowboyGameManager.movePlayerToBottom()
        }
        binding.tvActionGame.setOnClickListener {
            cowboyGameManager.startOrRestartGame()
        }
    }

    override fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int) {
        setCharacterMovement(player, iconDrawableRes)
    }

    override fun onGameStateChanged(gameState: GameState) {
        binding.tvStatusGame.text = ""
        binding.tvActionGame.text = when (gameState) {
            GameState.IDLE -> getString(R.string.text_fire)
            GameState.STARTED -> getString(R.string.text_fire)
            GameState.FINISHED -> getString(R.string.text_restart)
        }
    }

    override fun onGameFinished(gameState: GameState, winner: Player) {
        if (winner.playerSide == PlayerSide.PLAYER_ONE) {
            binding.tvStatusGame.text = getString(R.string.text_you_win)
        } else {
            binding.tvStatusGame.text = getString(R.string.text_you_lose)
        }
    }

    private fun setCharacterMovement(player: Player, iconDrawableRes: Int) {
        val ivCharTop: ImageView?
        val ivCharMiddle: ImageView?
        val ivCharBottom: ImageView?
        val drawable = ContextCompat.getDrawable(this, iconDrawableRes)

        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            ivCharTop = binding.ivPlayerLeftTop
            ivCharMiddle = binding.ivPlayerLeftMid
            ivCharBottom = binding.ivPlayerLeftBottom
        } else {
            ivCharTop = binding.ivPlayerRightTop
            ivCharMiddle = binding.ivPlayerRightMid
            ivCharBottom = binding.ivPlayerRightBottom
        }

        when (player.playerPosition) {
            PlayerPosition.TOP -> {
                ivCharTop.visibility = View.VISIBLE
                ivCharMiddle.visibility = View.INVISIBLE
                ivCharBottom.visibility = View.INVISIBLE
                ivCharTop.setImageDrawable(drawable)
            }
            PlayerPosition.MIDDLE -> {
                ivCharTop.visibility = View.INVISIBLE
                ivCharMiddle.visibility = View.VISIBLE
                ivCharBottom.visibility = View.INVISIBLE
                ivCharMiddle.setImageDrawable(drawable)

            }
            PlayerPosition.BOTTOM -> {
                ivCharTop.visibility = View.INVISIBLE
                ivCharMiddle.visibility = View.INVISIBLE
                ivCharBottom.visibility = View.VISIBLE
                ivCharBottom.setImageDrawable(drawable)
            }
        }
    }
}