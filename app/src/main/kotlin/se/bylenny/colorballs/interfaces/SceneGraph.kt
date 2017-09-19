package se.bylenny.colorballs.interfaces

import android.content.Entity

interface Scene {

    fun addEntity(entity: Entity)
}

class SceneGraph: Scene {
    override fun addEntity(entity: Entity) {
        
    }

}