package com.example.gameqwestion.domain.useCase

import com.example.gameqwestion.domain.entity.GameSettings
import com.example.gameqwestion.domain.entity.Level
import com.example.gameqwestion.domain.repisitory.GameRepository

class GetGameSettingUseCase(
    private val repository: GameRepository
) {


    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}