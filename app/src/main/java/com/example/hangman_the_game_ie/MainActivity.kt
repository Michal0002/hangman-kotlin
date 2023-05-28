package com.example.hangman_the_game_ie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.hangman_the_game_ie.dbase.MyDatabaseHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button_signIn = findViewById<Button>(R.id.button_signIn)
        button_signIn.setOnClickListener{
            val username = findViewById<EditText>(R.id.editTextText_username1).text.toString()
            val password = findViewById<EditText>(R.id.editTextText_password).text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = MyDatabaseHelper(this)
            val loggedIn = dbHelper.loginUser(username, password)

            if (loggedIn) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Hangman_main::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid credentials with $username and pwd $password", Toast.LENGTH_SHORT).show()
            }
        }

        val sign_up = findViewById<Button>(R.id.button_registration)
        sign_up.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java )
            startActivity(intent)
        }
        val button_google = findViewById<ImageButton>(R.id.button_loginGoogle)
        button_google.setOnClickListener{
            Toast.makeText(this, "Google account.", Toast.LENGTH_SHORT).show()
        }
        val button_facebook = findViewById<ImageButton>(R.id.button_loginFacebook)
        button_facebook.setOnClickListener{
            Toast.makeText(this, "Facebook account.", Toast.LENGTH_SHORT).show()
        }
//        val textViewUsers = findViewById<TextView>(R.id.textView_abc)
//        val dbHelper = MyDatabaseHelper(this)
//        val users = dbHelper.getPasswords()
//        val usersText = users.joinToString("\n") // Łączy nazwy użytkowników w jedną długą linię tekstu
//        textViewUsers.text = usersText
    }
}