package com.example.a2_rita.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a2_rita.databinding.CustomRowLayoutBinding
import com.example.a2_rita.models.Guess
import com.example.a2_rita.models.Result

class GuessAdapter : ListAdapter<Guess, GuessAdapter.GuessViewHolder>(GuessDiffCallback())  {

    // view holder definition
    inner class GuessViewHolder(val binding: CustomRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {}

    // implement the DiffUtil class
    class GuessDiffCallback: DiffUtil.ItemCallback<Guess>() {
        override fun areItemsTheSame(oldItem: Guess, newItem: Guess): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Guess, newItem: Guess): Boolean {
            return oldItem == newItem
        }
    }

    // mandatory functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuessViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomRowLayoutBinding.inflate(layoutInflater, parent, false)
        return GuessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuessViewHolder, position: Int) {
        // specify what data should be placed in each UI element of the custom row layout
        // 1. get the current guess from the list
        val currGuess = getItem(position)
        val state = currGuess.getLetterStates()
        // 2. associate the guess's information with the UI in the row layout
        holder.binding.txtChar0.text = currGuess.userGuess[0].toString()
        holder.binding.txtChar1.text = currGuess.userGuess[1].toString()
        holder.binding.txtChar2.text = currGuess.userGuess[2].toString()
        holder.binding.txtChar3.text = currGuess.userGuess[3].toString()
        holder.binding.txtChar4.text = currGuess.userGuess[4].toString()

        holder.binding.txtChar0.setBackgroundColor(Color.parseColor(state[0].colour))
        holder.binding.txtChar1.setBackgroundColor(Color.parseColor(state[1].colour))
        holder.binding.txtChar2.setBackgroundColor(Color.parseColor(state[2].colour))
        holder.binding.txtChar3.setBackgroundColor(Color.parseColor(state[3].colour))
        holder.binding.txtChar4.setBackgroundColor(Color.parseColor(state[4].colour))
    }

}