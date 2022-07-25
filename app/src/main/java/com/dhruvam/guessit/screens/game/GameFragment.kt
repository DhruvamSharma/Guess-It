package com.dhruvam.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.dhruvam.guessit.R
import com.dhruvam.guessit.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    // binding for game fragment
    private lateinit var binding: FragmentGameBinding

    // viewmodel for storing data
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        // create or restore game view model
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // listen for word changes
        viewModel.gameFinishedEvent.observe(viewLifecycleOwner) {
           if (it) {
               gameFinished()
           }
        }

        viewModel.buzz.observe(viewLifecycleOwner) {
            if (it != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(it.pattern)
            }
        }

        return binding.root
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        viewModel.currentScore.value.let {
            val action =
                GameFragmentDirections.actionGameFragmentToScoreFragment(it ?: 0)
            findNavController().navigate(action)
        }

    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }
}