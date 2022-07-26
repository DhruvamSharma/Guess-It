package com.dhruvam.guessit.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.dhruvam.guessit.R
import com.dhruvam.guessit.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {
    private lateinit var binding: FragmentTitleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        binding.playGameButton.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }
        return binding.root
    }
}