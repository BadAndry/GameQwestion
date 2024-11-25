package com.example.gameqwestion.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gameqwestion.R
import com.example.gameqwestion.data.GameRepositoryImpl
import com.example.gameqwestion.domain.entity.GameQuestion
import com.example.gameqwestion.domain.entity.GameResult
import com.example.gameqwestion.domain.entity.GameSettings
import com.example.gameqwestion.domain.entity.Level
import com.example.gameqwestion.domain.useCase.GenerateQuestionUseCase
import com.example.gameqwestion.domain.useCase.GetGameSettingUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level,
): ViewModel() {

    private lateinit var gameSettings: GameSettings

    private val repository = GameRepositoryImpl


    private val getGameSettingUseCase = GetGameSettingUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private var timer: CountDownTimer? = null


    private val _formatedTime = MutableLiveData<String>()
    val formatedTime: LiveData<String>
        get() = _formatedTime

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _progresAnswers = MutableLiveData<String>()
    val progresAnswer: LiveData<String>
        get() = _progresAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }


   private fun startGame() {
       getGameSettings()
       startTimer()
       generatQuestion()
       updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generatQuestion()
    }

    private fun updateProgress () {
        val percent = calculatePercent()
        _percentOfRightAnswer.value = percent
        _progresAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers.toString(),
            gameSettings.minCountOfRightAnswer.toString())
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswer
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calculatePercent(): Int {
        if(countOfQuestions == 0){
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = _question.value?.rightAnswer
        if(number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSettings(){
        this.gameSettings = getGameSettingUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    private fun startTimer() {
       timer = object : CountDownTimer(
            gameSettings.gameToneInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ){
            override fun onTick(millisSecond: Long) {
                _formatedTime.value = formatedTime(millisSecond)
            }

            override fun onFinish() {
                finishGame()
                updateProgress()
            }
        }
        timer?.start()
    }

    private fun generatQuestion() {
           _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatedTime(millisSecond: Long): String {
        val seconds = millisSecond / MILLIS_IN_SECONDS
        val minutes = seconds / SECOND_IN_MINUTE
        val leftSeconds = seconds - (minutes * SECOND_IN_MINUTE)
        return String.format("%02d:%02d", minutes, leftSeconds)

    }


    private fun finishGame () {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true
                    && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()

    }

    companion object {
        const val MILLIS_IN_SECONDS = 1000L
        private const val SECOND_IN_MINUTE = 60
    }
}