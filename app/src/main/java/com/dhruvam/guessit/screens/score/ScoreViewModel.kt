package com.dhruvam.guessit.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {
    // final score
    private val _finalScore = MutableLiveData<Int>()
    val finalScore: LiveData<Int> get() = _finalScore

    // game restart event
    private val _gameRestartEvent = MutableLiveData<Boolean>()
    val gameRestartEvent: LiveData<Boolean> get() = _gameRestartEvent
    init {
        _finalScore.value = finalScore
    }

    fun onGameRestart() {
        _gameRestartEvent.value = true
        _gameRestartEvent.value = false
    }
}