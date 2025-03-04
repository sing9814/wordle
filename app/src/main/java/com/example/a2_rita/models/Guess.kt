package com.example.a2_rita.models

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="guess")
data class Guess(val chanceId:Int, val secretWord:String, val userGuess: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    fun getLetterStates():List<Result>{
        val resultList:MutableList<Result> = mutableListOf()
        Log.d("ABC", secretWord)
        Log.d("ABC", userGuess)
        for (i in 0..4){
            when{
                userGuess[i].lowercase() == secretWord[i].lowercase() -> resultList.add(Result.CORRECT)
                userGuess[i].lowercase() in secretWord.lowercase() -> resultList.add(Result.WRONG_POSITION)
                else -> resultList.add(Result.NOT_IN_WORD)
            }
        }
        return resultList
    }
}