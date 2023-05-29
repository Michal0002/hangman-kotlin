package com.example.hangman_the_game_ie.dbase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class MyDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME="Hangman"
        val DB_VERSION = 1
        val TABLE_NAME = "Users"
        val COL_ID = "id"
        val COL_EMAIL = "email"
        val COL_PASSWORD = "password"
        val COL_USERNAME = "username"
        val COL_COINS = "coins"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_EMAIL TEXT, $COL_PASSWORD TEXT, $COL_USERNAME TEXT, $COL_COINS INTEGER)"
        if (db != null) {
            db.execSQL(createTableQuery)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun addUser(username: String, password: String, email: String): Boolean {
        val db = this.writableDatabase

        val usernameExists = checkIfUsernameExists(db, username)
        val emailExists = checkIfEmailExists(db, email)

        if (usernameExists || emailExists) {
            Toast.makeText(context, "User already exists.", Toast.LENGTH_SHORT).show()
            return false
        }

        val values = ContentValues()
        values.put(COL_USERNAME, username)
        values.put(COL_PASSWORD, password)
        values.put(COL_EMAIL, email)

        val result = db.insert(TABLE_NAME, null, values)
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

        db.close()
        return result != -1L
    }
    @SuppressLint("Range")
    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COL_ID)
        val selection = "$COL_USERNAME = ? AND $COL_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        return count > 0
    }
    private fun checkIfUsernameExists(db: SQLiteDatabase, username: String): Boolean {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    private fun checkIfEmailExists(db: SQLiteDatabase, email: String): Boolean {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

}


//@SuppressLint("Range")
//fun getPasswords(): ArrayList<String> {
//    val passwords = ArrayList<String>()
//    val db = this.readableDatabase
//    val query = "SELECT ${MyDatabaseHelper.COL_PASSWORD} FROM ${MyDatabaseHelper.TABLE_NAME}"
//    val cursor: Cursor? = db.rawQuery(query, null)
//
//    cursor?.let {
//        val columnIndex = cursor.getColumnIndex(MyDatabaseHelper.COL_PASSWORD)
//        while (cursor.moveToNext()) {
//            val password = cursor.getString(columnIndex)
//            passwords.add(password)
//        }
//    }
//
//    cursor?.close()
//    db.close()
//    return passwords
//}


//@SuppressLint("Range")
//fun getUser(username: String): String? {
//    val db = this.readableDatabase
//    val query = "SELECT ${MyDatabaseHelper.COL_USERNAME}, ${MyDatabaseHelper.COL_PASSWORD} FROM ${MyDatabaseHelper.TABLE_NAME} WHERE ${MyDatabaseHelper.COL_USERNAME} = ?"
//    val cursor: Cursor? = db.rawQuery(query, arrayOf(username))
//    var password: String? = null
//
//    if (cursor?.moveToFirst() == true) {
//        password = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COL_PASSWORD))
//    }
//
//    cursor?.close()
//    db.close()
//    return password
//}
