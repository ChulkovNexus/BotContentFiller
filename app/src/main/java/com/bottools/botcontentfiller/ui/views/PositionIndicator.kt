package com.bottools.botcontentfiller.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.WorldMap

class PositionIndicator : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private var linePaint: Paint? = null
    private var mFloorPaint: Paint? = null
    private var posY = 0
    private var posX = 0
    private var map: WorldMap? = null
    val biomes = ArrayList<Biome>()

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

    fun setSize(map: WorldMap) {
        this.map = map
        postInvalidate()
    }

    fun setPosition(posX : Int, posY : Int) {
        this.posX = posX
        this.posY = posY
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (map == null)
            return

        val xLinesCount = map!!.tiles.size
        val yLinesCount = map!!.tiles[0].size

        val xBlockSize = measuredWidth.toFloat() / xLinesCount
        val yBlockSize = measuredHeight.toFloat() / yLinesCount
        canvas?.drawColor(Color.GREEN)
        for (i in 0..xLinesCount) {
            canvas?.drawLine((xBlockSize * i), 0f, (xBlockSize * i), measuredHeight.toFloat(), linePaint)
        }
        for (i in 0..yLinesCount) {
            canvas?.drawLine(0f, yBlockSize * i, measuredWidth.toFloat(), yBlockSize * i, linePaint)
        }
        map!!.tiles.forEachIndexed { i, it ->
            map!!.tiles[i].forEachIndexed { j, it ->
                val biome = biomes.firstOrNull { it.id == map!!.tiles[i][j].biomeId }
                if (biome != null) {
                    mFloorPaint!!.color = biome.color
                    canvas?.drawRect(xBlockSize * j, yBlockSize * i, xBlockSize * (j + 1), yBlockSize * (i + 1), mFloorPaint)
                } else if (isBiomFilled(i, j)) {
                    mFloorPaint!!.color = Color.MAGENTA
                    canvas?.drawRect(xBlockSize * j, yBlockSize * i, xBlockSize * (j + 1), yBlockSize * (i + 1), mFloorPaint)
                }
                if (map!!.tiles[i][j].isUnpassable == true) {
                    mFloorPaint!!.color = Color.BLACK
                    canvas?.drawCircle((xBlockSize * j) + (xBlockSize / 2), (yBlockSize * i) + (yBlockSize / 2), Math.min(yBlockSize, xBlockSize) / 2, mFloorPaint)
                }
            }
        }
        mFloorPaint!!.color = Color.RED
        canvas?.drawRect(xBlockSize * posX, yBlockSize * posY, xBlockSize * (posX + 1), yBlockSize * (posY + 1), mFloorPaint)
    }

    private fun isBiomFilled(i: Int, j: Int) = !TextUtils.isEmpty(map!!.tiles[i][j].thisTileCustomDescription) && !TextUtils.isEmpty(map!!.tiles[i][j].nextTileCustomDescription) && !TextUtils.isEmpty(map!!.tiles[i][j].customFarBehindText)
}