package com.example.gameqwestion.data

import com.example.gameqwestion.domain.entity.GameQuestion
import com.example.gameqwestion.domain.entity.GameSettings
import com.example.gameqwestion.domain.entity.Level
import com.example.gameqwestion.domain.repisitory.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {


    private const val MIN_VALUE_SUM = 2
    private const val MIN_ANSWER_NUMBER = 1


    override fun generateQuestion(maxSumValue: Int, countOfVariance: Int): GameQuestion {
        val sum = Random.nextInt(MIN_VALUE_SUM, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_NUMBER, sum)
        val variantAnswer = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        variantAnswer.add(rightAnswer)
        val from = max(rightAnswer - countOfVariance, MIN_ANSWER_NUMBER)
        val to = min (maxSumValue, rightAnswer + countOfVariance)
        while (variantAnswer.size < countOfVariance) {
            variantAnswer.add(Random.nextInt(from, to))
        }
        return GameQuestion(sum,visibleNumber,variantAnswer.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> {
                GameSettings(
                    10,
                    4,
                    50,
                    10
                )
            }
                Level.EASY -> {
                    GameSettings(
                        10,
                        4,
                        50,
                        60
                    )
                }
                    Level.NORMAL -> {
                    GameSettings(
                        20,
                        7,
                        70,
                        50
                    )
                    }
                    Level.HARD -> {
                    GameSettings(
                        30,
                        10,
                        90,
                        40
                    )
            }
        }
    }
}