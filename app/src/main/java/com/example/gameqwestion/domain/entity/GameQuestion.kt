package com.example.gameqwestion.domain.entity

data class GameQuestion(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    val rightAnswer: Int
    get() = sum - visibleNumber
}