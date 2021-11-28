package com.danielg07.gato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class PlayerSetup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_setup)

    }

    fun submitButtonClick(view: View){
        val player1 : EditText = findViewById(R.id.personName1ET)
        val player2 : EditText = findViewById(R.id.personName2ET)

        val player1Name = player1.text.toString()
        val player2Name = player2.text.toString()

        val intent = Intent(this, GameDisplay::class.java)
        intent.putExtra("PLAYER_NAMES", arrayOf(player1Name, player2Name))

        startActivity(intent)

    }
}