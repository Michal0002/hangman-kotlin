package com.example.hangman_the_game_ie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class AdNewWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_new_word)

        val spinner: Spinner = findViewById(R.id.spinner_difficultyNewWord)
// Tworzenie listy elementów
        val items = listOf("hard", "medium", "easy")
// Tworzenie ArrayAdaptera
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
// Określanie stylu dla rozwijanej listy
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Przypisywanie adaptera do Spinnera
        spinner.adapter = adapter

        val newWordName = findViewById<EditText>(R.id.editTextText_wordName)
        val selectedDifficult = findViewById<Spinner>(R.id.spinner_difficultyNewWord)
        val buttonAddNewWord = findViewById<Button>(R.id.button_addNewWord)

        buttonAddNewWord.setOnClickListener{
            val word = newWordName?.text.toString()
            val difficult = selectedDifficult?.selectedItem?.toString()

            if (word != null && difficult != null) {
                val dbHelper = MyDatabaseHelper(this)
                val newWord = dbHelper.addWord(word, difficult)

                if (newWord) {
                    Toast.makeText(this, "Dodano nowe słówko do bazy: $word, $difficult", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Coś poszło nie tak i nie dodano słowa", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Wprowadź wartości dla nowego słowa i trudności.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}