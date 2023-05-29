package com.example.hangman_the_game_ie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class GameCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_category)
        val spinner: Spinner = findViewById(R.id.spinner_difficulty)
// Tworzenie listy elementów
        val items = listOf("Hard", "Medium", "Easy")
// Tworzenie ArrayAdaptera
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
// Określanie stylu dla rozwijanej listy
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Przypisywanie adaptera do Spinnera
        spinner.adapter = adapter

        val username = intent.getStringExtra("username").toString()
        val dbHelper = MyDatabaseHelper(this)
        val coins = dbHelper.getCoinsForUser(username)
        val coinsy = findViewById<TextView>(R.id.textView16)
        coinsy.text = coins.toString()

    }
}