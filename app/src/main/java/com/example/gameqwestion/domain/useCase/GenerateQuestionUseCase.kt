package com.example.gameqwestion.domain.useCase

import com.example.gameqwestion.domain.entity.GameQuestion
import com.example.gameqwestion.domain.repisitory.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {


    operator fun invoke(maxSumValue: Int): GameQuestion{
        return repository.generateQuestion(maxSumValue, COUNT_OF_VARIANCE)
    }


    private companion object {
        const val COUNT_OF_VARIANCE = 6
    }
}