package se.bylenny.colorballs.interfaces

import se.bylenny.colorballs.Pointer

interface Controller {
    fun onPress(pointers: Map<Int, Pointer>): Boolean
    fun onRelease(pointers: Map<Int, Pointer>): Boolean
    fun onMove(pointers: Map<Int, Pointer>): Boolean
    fun onCancel(pointers: Map<Int, Pointer>): Boolean
    fun onOutside(pointers: Map<Int, Pointer>): Boolean
    fun onButtonPress(pointers: Map<Int, Pointer>): Boolean
    fun onButtonRelease(pointers: Map<Int, Pointer>): Boolean
}