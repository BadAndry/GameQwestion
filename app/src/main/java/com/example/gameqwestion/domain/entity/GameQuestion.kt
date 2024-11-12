package com.example.gameqwestion.domain.entity

data class GameQuestion(
    val sum: Int,
    val visibleNumber: Int,
    val answerVariance: List<Int>
)