package com.example.a2_rita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2_rita.adapters.GuessAdapter
import com.example.a2_rita.databinding.ActivityMainBinding
import com.example.a2_rita.db.MyDatabase
import com.example.a2_rita.models.Guess
import com.example.a2_rita.viewmodels.MainActivityViewModel
import com.example.a2_rita.viewmodels.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val model: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(MyDatabase.getDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView
        val guessAdapter = GuessAdapter()
        binding.rvGuessList.layoutManager = LinearLayoutManager(this)
        binding.rvGuessList.adapter = guessAdapter

        // observer on list of guess data
        val guessListObserver = Observer<List<Guess>> {
                updatedGuessList ->
            Log.d("ABC", "Observer noticed that list of guess changed")
            guessAdapter.submitList(updatedGuessList)
        }

        model.guessList.observe(this, guessListObserver)

        val isGameOverObserver = Observer<Boolean> {
            isGameOver ->
            if(isGameOver){
                binding.btnSubmit.isEnabled = false
                this.binding.txtWinOrLose.text = if (model.isWin) "Win" else "Lose"
            }else{
                this.binding.txtWinOrLose.text = ""
            }
        }
        model.isGameOver.observe(this, isGameOverObserver)

        binding.btnSubmit.setOnClickListener {

            val input = binding.txtGuess.text.toString().uppercase()

            // validation that mandatory fields were filled in
            if (input.length != 5) {
                Log.d("ABC-VM", "no.")
                return@setOnClickListener
            }
            // using the view model, insert it into the DB using your DAO
            model.secretWord(input)

            // clear the text boxes and prepare for new input
            binding.txtGuess.setText("")
        }

        binding.btnSave.setOnClickListener{
            model.saveGame()
        }

        binding.btnNew.setOnClickListener {
            // using the view model, delete guess from Room database
            model.deleteAll()
            binding.btnSubmit.isEnabled = true
            binding.txtWinOrLose.text = ""
        }
    }
}