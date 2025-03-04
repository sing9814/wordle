package com.example.a2_rita.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.a2_rita.db.MyDatabase
import com.example.a2_rita.models.Guess

class MainActivityViewModel(val db: MyDatabase): ViewModel() {
    var currentGuess: LiveData<List<Guess>>
    init {
        Log.d("ABC", "vm is initializing")
        currentGuess = db.getGuessDAO().getAllGuess().asLiveData()
    }

    //secretWord: A string containing the game’s secret word. Use the word “CABLE”.
    private var secretWord:String = "CABLE"

    //guessList: A list of Guess objects. This list represents the user’s current guesses.
    var guessList:MutableLiveData<MutableList<Guess>> = MutableLiveData(mutableListOf())

    //isGameOver: A boolean variable to track whether the game is over.
    var isGameOver: MutableLiveData<Boolean> = MutableLiveData(false)

    var isWin = false

    //saveGame(): A function that saves the user’s guesses to an appropriate Room database entity
    fun saveGame() {
        if (guessList.value!!.isNotEmpty()){
            for (i in 0 until guessList.value!!.size){
                db.getGuessDAO().insert(guessList.value!![i])
            }
        }
    }

    fun deleteAll() {
        db.getGuessDAO().deleteAllGuess()
        guessList.value = mutableListOf()
    }

    // secretWord(userGuess): A function that accepts a String value containing the user’s guess.
    fun secretWord(userGuess: String){

        //1. Add the guess to the user’s list of current guesses
        val newGuesses = mutableListOf<Guess>()

        for(i in 0 until guessList.value!!.size){
            newGuesses.add(guessList.value!![i])
        }

        guessList.value = newGuesses
        guessList.value!!.add(Guess(1,secretWord, userGuess))

        //2. Check if the game is over.
        if(userGuess.uppercase() == secretWord.uppercase()){
            isWin = true
            isGameOver.value = true
        }

        if (guessList.value!!.size >= 5){ //2
            isWin = false
            isGameOver.value = true

            db.getGuessDAO().deleteAllGuess()
        }
    }
}

// factory method is needed because view model accepts a constructor
class MainActivityViewModelFactory(private val db:MyDatabase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}