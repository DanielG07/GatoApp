package com.danielg07.gato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class GameDisplay : AppCompatActivity() {
    private lateinit var ticTacTocBoard: TicTacTocBoard


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_display)

        val playAgainBTN: Button = findViewById(R.id.playAgainButton)
        val homeBTN: Button = findViewById(R.id.homeButton)
        val playerTurn: TextView = findViewById(R.id.gameTitleTV)
        val playerNames: Array<String> = intent.getStringArrayExtra("PLAYER_NAMES") as Array<String>

        playAgainBTN.visibility = View.GONE
        homeBTN.visibility = View.GONE


        if(playerNames != null && playerNames[0] != ""){
            playerTurn.text = "${playerNames[0]}'s Turn"
        }
        else{
            playerTurn.text = "Player 1's Turn"
        }

        ticTacTocBoard  = findViewById(R.id.ticTacTocBoard)

        ticTacTocBoard.setupGame(playAgainBTN, homeBTN, playerTurn, playerNames)
    }

    fun playAgainButtonClick(view: View){


        ticTacTocBoard.resetGame()
        ticTacTocBoard.invalidate()
    }

    fun homeButtonClick(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}