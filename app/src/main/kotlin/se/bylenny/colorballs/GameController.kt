package se.bylenny.colorballs

import se.bylenny.colorballs.interfaces.Controller

class GameController: Controller {
    override fun onPress(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

    override fun onRelease(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

    override fun onMove(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

    override fun onCancel(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

    override fun onOutside(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

    override fun onButtonPress(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

    override fun onButtonRelease(pointers: Map<Int, Pointer>): Boolean {
        return false
    }

}