package se.bylenny.colorballs

import android.graphics.Canvas

interface Renderer {
    fun setup(width: Int, height: Int)
    fun render(canvas: Canvas)
    fun update()
    fun getName(): String
}