package com.danielg07.gato

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView


class TicTacTocBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var boardColor: Int
    private var XColor: Int
    private var OColor: Int
    private var winningLineColor: Int

    private val paint = Paint()
    private var cellSize = width/3

    private var winningLine = false

    private val game = GameLogic()

    init {
        val a: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.TicTacTocBoard, 0, 0)
        try {
            boardColor = a.getInteger(R.styleable.TicTacTocBoard_boardColor, 0)
            XColor = a.getInteger(R.styleable.TicTacTocBoard_XColor, 0)
            OColor = a.getInteger(R.styleable.TicTacTocBoard_OColor, 0)
            winningLineColor = a.getInteger(R.styleable.TicTacTocBoard_winningLineColor, 0)
        }finally {
            a.recycle()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val dimensions = Math.min(measuredWidth, measuredHeight)
        cellSize = dimensions/3

        setMeasuredDimension(dimensions, dimensions)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        drawGameBoard(canvas)
        drawMarkers(canvas)

        if(winningLine){
            drawWinningLine(canvas)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent) : Boolean{
        var x = event.x
        var y = event.y
        var action = event.action

        if(action == MotionEvent.ACTION_DOWN){
            var row = Math.ceil((y/cellSize).toDouble()).toInt()
            var col = Math.ceil((x/cellSize).toDouble()).toInt()

            if (!winningLine){
                if(game.updateGameBoard(row, col)){
                    invalidate()

                    if (game.winnerCheck()){
                        winningLine = true
                    }

                    if(game.player % 2 == 0){
                        game.player = game.player - 1
                    }
                    else{
                        game.player = game.player + 1
                    }
                }
            }


            invalidate()

            return true
        }

        return false

    }

    private fun drawGameBoard(canvas: Canvas?){
        paint.color = boardColor
        paint.strokeWidth = 16.toFloat()

        for (c in 1..2){
            canvas!!.drawLine(cellSize*c.toFloat(), 0.toFloat(), cellSize*c.toFloat(), canvas.width.toFloat(), paint)
        }

        for (r in 1..2){
            canvas!!.drawLine(0.toFloat(), cellSize*r.toFloat(), canvas.width.toFloat(), cellSize*r.toFloat(), paint)
        }
    }

    private fun drawMarkers(canvas: Canvas?){
        for (r in 0..2){
            for (c in 0..2){
                if(game.gameBoard!![r][c] != 0){
                    if(game.gameBoard!![r][c] == 1){
                        drawX(canvas, r, c)
                    }
                    else{
                        drawO(canvas, r, c)
                    }
                }
            }
        }
    }

    private fun drawX(canvas: Canvas?, row: Int, col: Int){
        paint.color = XColor

        canvas!!.drawLine(((col+1)*cellSize - cellSize*0.2).toFloat(),
            (row*cellSize + cellSize*0.2).toFloat(),
            (col*cellSize + cellSize*0.2).toFloat(),
            ((row+1)*cellSize - cellSize*0.2).toFloat(), paint)

        canvas!!.drawLine((col*cellSize + cellSize*0.2).toFloat(),
            (row*cellSize + cellSize*0.2).toFloat(),
            ((col+1)*cellSize - cellSize*0.2).toFloat(),
            ((row+1)*cellSize - cellSize*0.2).toFloat(), paint)

    }

    private fun drawO(canvas: Canvas?, row: Int, col: Int){
        paint.color = OColor

        canvas!!.drawOval((col*cellSize + cellSize*0.2).toFloat(),
            (row*cellSize + cellSize*0.2).toFloat(),
            (col*cellSize + cellSize*0.8).toFloat(),
            (row*cellSize + cellSize*0.8).toFloat(), paint)
    }

    private fun drawHorizontalLine(canvas: Canvas?, row: Int, col: Int){

        canvas!!.drawLine(col.toFloat(),
            (row*cellSize + cellSize/2).toFloat(),
            (cellSize*3).toFloat(),
            (row*cellSize + cellSize/2).toFloat(), paint)
    }

    private fun drawVerticalLine (canvas: Canvas?, row: Int, col: Int){

        canvas!!.drawLine((col*cellSize + cellSize/2).toFloat(),
            (row).toFloat(),
            (col*cellSize + cellSize/2).toFloat(),
            (cellSize*3).toFloat(), paint)
    }

    private fun drawDiagonalLinePos (canvas: Canvas?){

        canvas!!.drawLine((0).toFloat(),
            (cellSize*3).toFloat(),
            (cellSize*3).toFloat(),
            (0).toFloat(), paint)
    }

    private fun drawDiagonalLineNeg (canvas: Canvas?){

        canvas!!.drawLine((0).toFloat(),
            (0).toFloat(),
            (cellSize*3).toFloat(),
            (cellSize*3).toFloat(), paint)
    }

    fun drawWinningLine (canvas: Canvas?){
        paint.color = winningLineColor

        val row = game.winType[0]
        val col = game.winType[1]
        val type = game.winType[2]

        when(type){
            1 -> drawHorizontalLine(canvas, row, col)
            2 -> drawVerticalLine(canvas, row, col)
            3 -> drawDiagonalLineNeg(canvas)
            4 -> drawDiagonalLinePos(canvas)

        }

    }

    fun setupGame(playAgain: Button, home: Button, playerDisplay: TextView, name: Array<String>){
        game.playAgainBTN = playAgain
        game.homeBTN = home
        game.playerTurn = playerDisplay
        if(name[0] != "" && name[1] != ""){
            game.playerNames = name
        }

    }

    fun resetGame(){
        game.resetGame()
        winningLine = false
    }


}