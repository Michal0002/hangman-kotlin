package com.example.hangman_the_game_ie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class AllWordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_words)


        val dbHelper = MyDatabaseHelper(this)

        val listOfAllWords = findViewById<ListView>(R.id.listView_AllWords)
        val words = dbHelper.getWords()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, words)
        listOfAllWords.adapter = adapter

        val name_filter = findViewById<EditText>(R.id.editTextText_nameOfWord)

    }

}