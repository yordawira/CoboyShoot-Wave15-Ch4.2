package com.catty.coboyshoot_wave15_ch42.model

import com.catty.coboyshoot_wave15_ch42.enum.PlayerPosition
import com.catty.coboyshoot_wave15_ch42.enum.PlayerSide
import com.catty.coboyshoot_wave15_ch42.enum.PlayerState

data class Player(
    val playerSide: PlayerSide,
    var playerState: PlayerState,
    var playerPosition: PlayerPosition
)