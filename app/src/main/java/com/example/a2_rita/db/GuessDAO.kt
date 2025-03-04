package com.example.a2_rita.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.a2_rita.models.Guess
import kotlinx.coroutines.flow.Flow

@Dao
interface GuessDAO {
    // functions that represent what operations you plan on performing on the guess entity

    // insert
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insert(item: Guess)

    // getAll
    @Query("SELECT * FROM guess")
    fun getAllGuess(): Flow<List<Guess>>

    @Query("DELETE FROM guess")
    fun deleteAllGuess()
}