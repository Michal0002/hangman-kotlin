package com.example.hangman_the_game_ie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class Hangman_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman_main)
        val username = intent.getStringExtra("username").toString()
        val textbox = findViewById<TextView>(R.id.textView2)
        textbox.text = "Welcome $username"

        val dbHelper = MyDatabaseHelper(this)
        val coins = dbHelper.getCoinsForUser(username)
        val coinsy = findViewById<TextView>(R.id.textView3)
        coinsy.text = coins.toString()
        val button_play = findViewById<Button>(R.id.button_play)
        button_play.setOnClickListener{
            val intent = Intent(this, GameCategoryActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("coins", coins)
            startActivity(intent)
        }
        val button_addnew = findViewById<Button>(R.id.button_newWord)
        button_addnew.setOnClickListener{
            val intent = Intent(this, AdNewWordActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("coins", coins)
            startActivity(intent)
        }

        val textView5 = findViewById<TextView>(R.id.textView5)
        val words = dbHelper.getWords()
        val wordsText = words.joinToString(", ") // Łączysz słowa w jedną linię tekstu oddzieloną przecinkami

        textView5.text = wordsText

    }
}