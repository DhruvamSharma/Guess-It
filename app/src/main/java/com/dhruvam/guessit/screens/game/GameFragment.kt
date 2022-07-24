package com.dhruvam.guessit.screens.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.dhruvam.guessit.R
import com.dhruvam.guessit.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    // current word
    private var currentWord = ""
    // current score
    private var currentScore = 0
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        // create a new list and shuffle
        resetList()
        // assign the word and score for the first time
        nextWord()

        // assign listeners to skip and play button
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }

        return binding.root
    }


    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(currentScore)
        findNavController().navigate(action)
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            gameFinished()
        } else {
            currentWord = wordList.removeAt(0)
        }
        updateWordText()
        updateScoreText()
    }

    /** Methods for buttons presses **/

    private fun onSkip() {
        currentScore--
        nextWord()
    }

    private fun onCorrect() {
        currentScore++
        nextWord()
    }

    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = currentWord
    }
    private fun updateScoreText() {
        binding.scoreText.text = currentScore.toString()
    }
}