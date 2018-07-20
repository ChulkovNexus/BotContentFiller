package com.bottools.botcontentfiller.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

class PositionIndicator : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private var linePaint: Paint? = null
    private var mFloorPaint: Paint? = null
    private var sizeY = 0
    private var sizeX = 0
    private var posY = 0
    private var posX = 0

    init {
        setWillNotDraw(false)
        this.linePaint = Paint(1)
        this.linePaint!!.color = Color.BLACK
        this.linePaint!!.style = Paint.Style.STROKE
        this.linePaint!!.strokeWidth = 1f
        this.linePaint!!.isAntiAlias = true

        this.mFloorPaint = Paint(1)
        this.mFloorPaint!!.color = Color.RED
        this.mFloorPaint!!.style = Paint.Style.FILL
        this.mFloorPaint!!.isAntiAlias = true
    }


    fun setSize(sizeX : Int, sizeY : Int) {
        this.sizeX = sizeX
        this.sizeY = sizeY
        postInvalidate()
    }


    fun setPosition(posX : Int, posY : Int) {
        this.posX = posX
        this.posY = posY
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val xLinesCount = sizeX
        val yLinesCount = sizeY
        val xBlockSize = measuredWidth.toFloat()/ xLinesCount
        val yBlockSize = measuredHeight.toFloat()/ yLinesCount
        canvas?.drawColor(Color.GREEN)
        for (i in 0..xLinesCount) {
            canvas?.drawLine((xBlockSize * i), 0f, (xBlockSize * i), measuredHeight.toFloat(), linePaint)
        }
        for (i in 0..yLinesCount) {
            canvas?.drawLine(0f, yBlockSize * i, measuredWidth.toFloat(),yBlockSize * i, linePaint)
        }
        canvas?.drawRect(xBlockSize * posX, yBlockSize * posY,xBlockSize * (posX+1), yBlockSize * (posY + 1), mFloorPaint)

    }
}