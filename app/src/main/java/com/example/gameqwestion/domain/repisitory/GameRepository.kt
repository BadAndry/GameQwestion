package com.example.gameqwestion.domain.repisitory

import com.example.gameqwestion.domain.entity.GameQuestion
import com.example.gameqwestion.domain.entity.GameSettings
import com.example.gameqwestion.domain.entity.Level

interface GameRepository {


    fun generateQuestion(
        maxSumValue: Int,
        countOfVariance: Int
        ): GameQuestion


    fun getGameSettings(level: Level): GameSettings
}