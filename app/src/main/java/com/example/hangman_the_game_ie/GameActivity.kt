package com.example.hangman_the_game_ie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val username = intent.getStringExtra("username").toString()
        val selectedDifficulty = intent.getStringExtra("selectedDifficulty").toString()
        val dbHelper = MyDatabaseHelper(this)
        val coins = dbHelper.getCoinsForUser(username)
        val coinsy = findViewById<TextView>(R.id.textView10)
        coinsy.text = coins.toString()

        val word = findViewById<TextView>(R.id.textView_word)
        val randomWord = dbHelper.getRandomWord(selectedDifficulty)

        if (randomWord != null) {
            val hiddenWord = randomWord.replace("[A-Za-z]".toRegex(), " _ ")
            word.text = hiddenWord
        } else {
            word.text = "Brak dostępnych słów"
        }

        val lettersUsed = mutableListOf<Char>()
        val textViewLettersUsed = findViewById<TextView>(R.id.textView_lettersused)
        val editTextLetter = findViewById<EditText>(R.id.editTextText_letter)

        val imageViewAttempts = findViewById<ImageView>(R.id.imageView_attempts)
        val baseResourceName = "hangman" // Nazwa bazowa dla zasobów
        var currentAttempt = 0 // Aktualna liczba prób

        fun changeAttemptsImage() {
            val resourceName = baseResourceName + currentAttempt
            val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
            imageViewAttempts.setImageResource(resourceId)
        }

        val button_guess = findViewById<Button>(R.id.button_guess)
        button_guess.setOnClickListener{
            val guessedLetter = editTextLetter.text.toString().trim().uppercase().singleOrNull()
            if (guessedLetter != null && guessedLetter in 'A'..'Z') {
                if (guessedLetter in lettersUsed) {
                    Toast.makeText(this, "Ta litera została już użyta.", Toast.LENGTH_SHORT).show()
                } else {
                    lettersUsed.add(guessedLetter)
                    textViewLettersUsed.text = lettersUsed.joinToString(" ")
                }
            } else {
                Toast.makeText(this, "Wprowadź pojedynczą literę od A do Z.", Toast.LENGTH_SHORT).show()
            }

            currentAttempt++
            if (currentAttempt <= 11) {
                changeAttemptsImage()
                Toast.makeText(this, "Incorrect letter.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "You lost.", Toast.LENGTH_SHORT).show()
            }
            editTextLetter.text.clear()

        }
    }
}