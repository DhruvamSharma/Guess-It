package com.dhruvam.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)
class GameViewModel: ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    // current word
    private val _currentWord = MutableLiveData("")
    val currentWord: LiveData<String> get() = _currentWord
    // current score
    private val _currentScore = MutableLiveData(0)
    val currentScore: LiveData<Int> get() = _currentScore
    // event for game finished
    private val _gameFinishedEvent = MutableLiveData(false)
    val gameFinishedEvent: LiveData<Boolean> get() = _gameFinishedEvent
    // timer seconds
    private val _secondsLeft = MutableLiveData<String>()
    val secondsLeft: LiveData<String> get() = _secondsLeft
    // buzzer event
    private val _buzz = MutableLiveData<BuzzType>()
    val buzz: LiveData<BuzzType> get() = _buzz
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>
    // timer
    private val timer: CountDownTimer

    init {
        Log.i("GameViewModel", "created")
        resetList()
        nextWord()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(p0: Long) {
               val secondsLeft = p0 / ONE_SECOND
                _secondsLeft.value = DateUtils.formatElapsedTime(secondsLeft)
            }

            override fun onFinish() {
                _gameFinishedEvent.value = true
                _gameFinishedEvent.value = false
                _buzz.value = BuzzType.GAME_OVER
                _buzz.value = BuzzType.NO_BUZZ
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "destroyed")
        timer.cancel()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _currentWord.value = wordList.removeAt(0)
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

    /** Methods for buttons presses **/

    fun onSkip() {
        _currentScore.value = currentScore.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _currentScore.value = currentScore.value?.plus(1)
        nextWord()
        _buzz.value = BuzzType.CORRECT
    }
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }
}
