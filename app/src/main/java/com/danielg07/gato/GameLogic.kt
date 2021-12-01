package com.danielg07.gato

import android.view.View
import android.widget.Button
import android.widget.TextView

class GameLogic {
    var gameBoard: Array<IntArray>? = null

    var playAgainBTN: Button? = null
    var homeBTN: Button? = null
    var playerTurn: TextView? = null
    var playerNames= arrayOf("Player 1", "Player 2")

    var player = 1

    var winType: Array<Int> = arrayOf<Int>(-1, -1, -1)

    init {
        val one: IntArray = intArrayOf(0,0,0)
        val two: IntArray = intArrayOf(0,0,0)
        val three: IntArray = intArrayOf(0,0,0)
        gameBoard = arrayOf(one, two, three)

    }

    fun updateGameBoard(row: Int, col: Int): Boolean{
        if(gameBoard!![row-1][col-1] == 0){
            gameBoard!![row-1][col-1] = player;
            if(player == 1){
                playerTurn!!.text = "${playerNames[1]}'s Turn"
            }
            else{
                playerTurn!!.text = "${playerNames[0]}'s Turn"
            }

            return true
        }
        else{
            return false
        }
    }

    fun winnerCheck(): Boolean{
        var isWinner = false
        var boardFilled = 0

        for (r in 0..2){
            if(gameBoard!![r][0] == gameBoard!![r][1] && gameBoard!![r][0] == gameBoard!![r][2] && gameBoard!![r][0] != 0){
                isWinner = true
                winType = arrayOf<Int>(r, 0, 1)
            }
        }

        for (c in 0..2){
            if(gameBoard!![0][c] == gameBoard!![1][c] && gameBoard!![0][c] == gameBoard!![2][c] && gameBoard!![0][c] != 0){
                isWinner = true
                winType = arrayOf<Int>(0, c, 2)
            }
        }

        if(gameBoard!![0][0] == gameBoard!![1][1] && gameBoard!![0][0] == gameBoard!![2][2] && gameBoard!![0][0] != 0){
            isWinner = true
            winType = arrayOf<Int>(0, 0, 3)
        }

        if(gameBoard!![0][2] == gameBoard!![1][1] && gameBoard!![0][2] == gameBoard!![2][0] && gameBoard!![0][2] != 0){
            isWinner = true
            winType = arrayOf<Int>(0, 2, 4)
        }

        for (r in 0..2){
            for (c in 0..2){
                if(gameBoard!![r][c] != 0){
                    boardFilled += 1
                }
            }
        }

        if(isWinner){
            playAgainBTN!!.visibility = View.VISIBLE
            homeBTN!!.visibility = View.VISIBLE
            playerTurn!!.text = "${playerNames[player-1]} Won"
        }
        else if (boardFilled == 9){
            playAgainBTN!!.visibility = View.VISIBLE
            homeBTN!!.visibility = View.VISIBLE
            playerTurn!!.text = "Tie game"
            isWinner = true
            winType = arrayOf<Int>(-1, -1, -1)
        }

        return isWinner

    }

    fun resetGame(){
        for(r in 0..2){
            for(c in 0..2){
                gameBoard!![r][c] = 0
            }
        }

        player = 1
        playAgainBTN!!.visibility = View.GONE
        homeBTN!!.visibility = View.GONE
        playerTurn!!.text = "${playerNames[0]}'s Turn"


    }

}