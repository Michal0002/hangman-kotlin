package com.example.hangman_the_game_ie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class GameActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var selectedDifficulty: String
    private lateinit var dbHelper: MyDatabaseHelper
    private lateinit var randomWord: String
    private lateinit var hiddenWord: String
    private lateinit var lettersUsed: MutableList<Char>
    private var currentAttempt: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        username = intent.getStringExtra("username").toString()
        selectedDifficulty = intent.getStringExtra("selectedDifficulty").toString()
        dbHelper = MyDatabaseHelper(this)

        val coins = dbHelper.getCoinsForUser(username)
        val coins_value = findViewById<TextView>(R.id.textView10)
        coins_value.text = coins.toString()

        val word = findViewById<TextView>(R.id.textView_word)
        lettersUsed = mutableListOf()

        initializeGame()

        val textViewLettersUsed = findViewById<TextView>(R.id.textView_lettersused)
        val editTextLetter = findViewById<EditText>(R.id.editTextText_letter)
        val imageViewAttempts = findViewById<ImageView>(R.id.imageView_attempts)
        val buttonGuess = findViewById<Button>(R.id.button_guess)

        buttonGuess.setOnClickListener {
            val guessedLetter = editTextLetter.text.toString().trim().singleOrNull()?.toUpperCase()
            if (guessedLetter != null && guessedLetter in 'A'..'Z') {
                var correctGuess = false
                if (guessedLetter in lettersUsed) {
                    Toast.makeText(this, "Ta litera została już użyta.", Toast.LENGTH_SHORT).show()
                } else {
                    lettersUsed.add(guessedLetter)
                    textViewLettersUsed.text = lettersUsed.joinToString(" ")
                    for (i in randomWord.indices) {
                        if (randomWord[i].toUpperCase() == guessedLetter) {
                            val revealedWord = StringBuilder(hiddenWord)
                            revealedWord.setCharAt(i * 2, guessedLetter)
                            hiddenWord = revealedWord.toString()
                            word.text = hiddenWord
                            correctGuess = true
                        }
                    }
                    if (correctGuess) {
                        Toast.makeText(
                            this,
                            "Brawo! Litera $guessedLetter znajduje się w słowie",
                            Toast.LENGTH_SHORT
                        ).show()
                        if (!hiddenWord.contains('_')) {
                            dbHelper.updateUserCoins(username, coins + 50)
                            var intent = Intent(this, Hangman_main::class.java )
                            intent.putExtra("username", username)
                            intent.putExtra("coins", coins)
                            startActivity(intent)
                            Toast.makeText(this, "Wygrałeś! Dostajesz 50 monet", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        currentAttempt++
                        if (currentAttempt <= 11) {
                            val attemptsLeft = findViewById<TextView>(R.id.textView_attemptsLeft)
                            val attempts = (11 - currentAttempt)
                            attemptsLeft.text = ("Pozostała liczba prób: $attempts").toString() // Aktualizacja pozostałych prób
                            val resourceName = "hangman$currentAttempt"
                            val resourceId =
                                resources.getIdentifier(resourceName, "drawable", packageName)
                            imageViewAttempts.setImageResource(resourceId)
                            Toast.makeText(this, "Nieprawidłowa litera.", Toast.LENGTH_SHORT).show()
                        } else {
                            // Użytkownik przekroczył limit prób
                            // Dodaj odpowiednią logikę tutaj
                            Toast.makeText(this, "Przegrałeś.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Wprowadź pojedynczą literę od A do Z.", Toast.LENGTH_SHORT)
                    .show()
            }
            editTextLetter.text.clear()
        }
        val buttonHint = findViewById<Button>(R.id.button_hint)
        buttonHint.setOnClickListener {
            val hintLetter = getRandomHintLetter()
            val editTextLetter = findViewById<EditText>(R.id.editTextText_letter)
            editTextLetter.setText(hintLetter.toString())
        }

    }

    // Losujemy słówko z BD i zakrywamy je znakiem"_"
    private fun initializeGame() {
        randomWord = dbHelper.getRandomWord(selectedDifficulty) ?: "" //jeśli brak słowa zwracamy null
        hiddenWord = hideWord(randomWord)
        val word = findViewById<TextView>(R.id.textView_word)
//        val slowo = findViewById<TextView>(R.id.textView6)
        if (randomWord.isNotEmpty()) {
            word.text = hiddenWord.uppercase()
        } else {
            word.text = "Brak dostępnych słów w BD"
        }
    }

    // Ukrywamy słowo znakiem - "_"
    private fun hideWord(word: String): String {
        val hidden = StringBuilder()  // obiekt StringBuilder, dla ukrycia słowa
        for (i in word.indices) {  // iteracja po indeksach w słowie
            hidden.append("_ ")  // ukrywanie słowa
        }
        return hidden.toString()  // konwersja na strng
    }

    private fun getRandomHintLetter(): Char {
        val availableLetters = ('A'..'Z').filter { it !in lettersUsed }
        return availableLetters.random()
    }
}
