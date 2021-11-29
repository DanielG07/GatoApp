package com.danielg07.gato

class GameLogic {
    var gameBoard: Array<IntArray>? = null
    var player = 1

    init {
        val one: IntArray = intArrayOf(0,0,0)
        val two: IntArray = intArrayOf(0,0,0)
        val three: IntArray = intArrayOf(0,0,0)
        gameBoard = arrayOf(one, two, three)

    }

    fun updateGameBoard(row: Int, col: Int): Boolean{
        if(gameBoard!![row-1][col-1] == 0){
            gameBoard!![row-1][col-1] = player;
            return true
        }
        else{
            return false
        }
    }

}