package com.example.gameqwestion.presentation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.gameqwestion.R
import com.example.gameqwestion.R.*
import com.example.gameqwestion.databinding.FragmentGameFinishBinding
import com.example.gameqwestion.domain.entity.GameResult


class FragmentGameFinish : Fragment() {

    private lateinit var gameResult: GameResult

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinish = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgument()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()

                }
            })
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

    }

    private fun bindViews(){
        binding.emojiResult.setImageResource(emojeResult())

        binding.tvRequiredAnswers.text = String.format(getString(R.string.required_score),
            gameResult.gameSittings.minCountOfRightAnswer.toString()
        )
        binding.tvScoreAnswers.text = String.format(getString(R.string.score_answers),
            gameResult.countOfRightAnswers.toString()
        )
        binding.tvRequiredPercentage.text = String.format(getString(R.string.required_percentage),
            gameResult.gameSittings.minPercentOfRightAnswer.toString())
        binding.tvScorePercentage.text = String.format(getString(R.string.score_percentage),
            getPercentOfRightAnswer().toString())

    }

    private fun emojeResult(): Int {
        if (gameResult.winner) {
            return R.drawable.ic_smile
        }  else {
          return   R.drawable.ic_sad
        }
    }

    private fun getPercentOfRightAnswer() = with(gameResult){
        if (countOfRightAnswers == 0) {
             0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgument(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_GAME_RESULT, GameResult::class.java)?.let {
                gameResult = it
            }
        }
    }

    private fun retryGame(){
        requireActivity().supportFragmentManager.popBackStack(FragmentGame.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


companion object{

    private const val KEY_GAME_RESULT = "game_result"

    fun newInstance(gameResult: GameResult): FragmentGameFinish {
        return FragmentGameFinish().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_GAME_RESULT, gameResult)
            }
        }
    }
}
}