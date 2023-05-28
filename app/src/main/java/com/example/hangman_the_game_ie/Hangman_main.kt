package com.example.hangman_the_game_ie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Hangman_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman_main)

        val text = findViewById<TextView>(R.id.textView23)
    }
}