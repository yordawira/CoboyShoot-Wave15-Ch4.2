package com.catty.coboyshoot_wave15_ch42.ui.ingamestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.catty.coboyshoot_wave15_ch42.databinding.ActivityInGameStartBinding

class InGameStartActivity : AppCompatActivity() {

    private val binding: ActivityInGameStartBinding by lazy {
        ActivityInGameStartBinding.inflate(layoutInflater)
    }

    companion object {
        const val EXTRA_NAME: String = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTitleName()
        setOnClickListener()
    }

    private fun setTitleName() {
        val name = intent.getStringExtra(EXTRA_NAME)
        binding.tvName.text = buildString {
            append(" ")
            append(name)
        }
    }

    private fun setOnClickListener() {
        binding.ivVsComputer.setOnClickListener {
            Toast.makeText(this, "Player vs computer", Toast.LENGTH_SHORT).show()
        }
        binding.ivVsPlayer.setOnClickListener {
            Toast.makeText(this, "Player vs player", Toast.LENGTH_SHORT).show()
        }
    }
}