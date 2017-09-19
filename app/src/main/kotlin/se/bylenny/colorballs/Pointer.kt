package se.bylenny.colorballs

class Pointer {
    var id: Int = 0
    var x: Float = 0f
    var y: Float = 0f
    var preasure: Float = 0f
    var time: Long = 0
    var tool: Tool = Tool.UNKNOWN
    var button: Button = Button.UNKNOWN

    enum class Tool {
        UNKNOWN,
        FINGER,
        ERASER,
        STYLUS,
        MOUSE
    }

    enum class Button {
        UNKNOWN,
        BACK,
        FORWARD,
        PRIMARY,
        SECONDARY,
        STYLUS_PRIMARY,
        STYLUS_SECONDARY,
        TERTIARY
    }
}
