package se.bylenny.colorballs

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.canvas


class MainActivity : AppCompatActivity() {

    private val renderer = GameRenderer()
    private val controller = GameController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        canvas.setRenderer(renderer)
        canvas.setController(controller)
    }
}

