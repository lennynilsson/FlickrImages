package se.bylenny.colorballs

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import se.bylenny.colorballs.interfaces.Renderer

class GameRenderer: Renderer {

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
    }
    private lateinit var fullRect: Rect

    override fun setup(width: Int, height: Int) {
        fullRect = Rect(0, 0, width, height)
    }

    override fun render(canvas: Canvas) {
        canvas.drawRect(fullRect, paint)
    }

    override fun update() {

    }

    override fun getName(): String = "GameRenderer"
}