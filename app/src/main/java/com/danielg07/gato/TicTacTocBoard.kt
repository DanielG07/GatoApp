package com.danielg07.gato

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class TicTacTocBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var boardColor: Int = 0
    private var XColor: Int = 0
    private var OColor: Int = 0
    private var winningLineColor: Int = 0

    private val paint = Paint()
    private var cellSize = width/3

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
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent) : Boolean{
        var x = event.x
        var y = event.y
        var action = event.action

        if(action == MotionEvent.ACTION_DOWN){
            var row = Math.ceil((y/cellSize).toDouble()).toInt()
            var col = Math.ceil((x/cellSize).toDouble()).toInt()
            if(game.updateGameBoard(row, col)){
                invalidate()

                if(game.player % 2 == 0){
                    game.player = game.player - 1
                }
                else{
                    game.player = game.player + 1
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


}