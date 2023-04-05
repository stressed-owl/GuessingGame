package com.cute_white_bear.guessinggame.model

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val words = listOf(
        "Android", "Fragment", "Activity", "ViewModel", "UDF", "DataLayer",
        "UILayer", "StateFlow", "MutableStateFlow", "State", "Event"
    )
    private val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var incorrectGuesses = ""
    var correctGuesses = ""
    var lives = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(word: String) = when (correctGuesses.contains(word)) {
        true -> word
        false -> "_ "
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                lives--
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay, true)
    fun isLost() = lives <= 0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "YOU WON!"
        else if (isLost()) message = "YOU LOST!"
        message += " The word was $secretWord."
        return message
    }
}