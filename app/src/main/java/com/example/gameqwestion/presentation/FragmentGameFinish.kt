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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gameqwestion.R
import com.example.gameqwestion.R.*
import com.example.gameqwestion.databinding.FragmentGameFinishBinding
import com.example.gameqwestion.domain.entity.GameResult


class FragmentGameFinish : Fragment() {


    private val args by navArgs<FragmentGameFinishArgs>()

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinish = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameResult = args.gameResult
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun retryGame() {
            findNavController().popBackStack()
        }
    }
