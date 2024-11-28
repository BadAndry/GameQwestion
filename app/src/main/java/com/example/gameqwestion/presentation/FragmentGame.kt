package com.example.gameqwestion.presentation

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gameqwestion.R
import com.example.gameqwestion.databinding.FragmentGameBinding
import com.example.gameqwestion.domain.entity.GameResult
import com.example.gameqwestion.domain.entity.Level

class FragmentGame : Fragment() {

    private val args by navArgs<FragmentGameArgs>()


    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, args.level)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGame = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }



    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinish(it)
        }

    }
    private fun launchGameFinish(gameResult: GameResult) {
        findNavController().navigate(FragmentGameDirections.actionFragmentGameToFragmentGameFinish(gameResult))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    }
