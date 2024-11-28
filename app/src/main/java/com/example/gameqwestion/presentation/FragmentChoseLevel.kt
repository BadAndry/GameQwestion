package com.example.gameqwestion.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gameqwestion.R
import com.example.gameqwestion.databinding.FragmentChooseLevelBinding
import com.example.gameqwestion.domain.entity.Level
class FragmentChoseLevel : Fragment() {

    private var _binding: FragmentChooseLevelBinding?= null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChoseLevel = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLevelTest.setOnClickListener {
            launceChoseLevel(Level.TEST)
        }
        binding.buttonLevelEasy.setOnClickListener {
            launceChoseLevel(Level.EASY)
        }
        binding.buttonLevelNormal.setOnClickListener {
            launceChoseLevel(Level.NORMAL)
        }
        binding.buttonLevelHard.setOnClickListener {
            launceChoseLevel(Level.HARD)
        }

    }
    private fun launceChoseLevel(level: Level) {
        findNavController().navigate(FragmentChoseLevelDirections.actionFragmentChoseLevelToFragmentGame(level))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}