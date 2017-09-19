package se.bylenny.colorballs.models

class Rect(
    var x: Float = 0f,
    var y: Float = 0f,
    var w: Float = 0f,
    var h: Float = 0f
) {
    var x2: Float
        set(value) { w = x2 - x }
        get() = x + w
    var y2: Float
        set(value) { h = y2 - y }
        get() = y + h

    fun plus(other: Rect) = Rect(
        Math.min(x, other.x),
        Math.min(y, other.y),
        Math.max(x2, other.x2),
        Math.max(y2, other.y2)
    )
}