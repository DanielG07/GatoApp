package com.danielg07.gato

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TicTacTocBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var boardColor: Int = 0
    private var XColor: Int = 0
    private var OColor: Int = 0
    private var winningLineColor: Int = 0

    private val paint = Paint()
    private var cellSize = width/3

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


}