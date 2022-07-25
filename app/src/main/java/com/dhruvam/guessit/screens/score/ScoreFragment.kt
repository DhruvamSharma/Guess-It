package com.dhruvam.guessit.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dhruvam.guessit.R
import com.dhruvam.guessit.databinding.FragmentScoreBinding

/**
 * A simple [Fragment] subclass.
 */
class ScoreFragment : Fragment() {
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var binding: FragmentScoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)

        // fetch the arguments
        val args by navArgs<ScoreFragmentArgs>()
        viewModelFactory = ScoreViewModelFactory(args.currentScore)
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.gameRestartEvent.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToTitleFragment())
            }
        }
        return binding.root
    }
}