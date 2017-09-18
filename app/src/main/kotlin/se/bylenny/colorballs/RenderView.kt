package se.bylenny.colorballs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.TextureView
import kotlinx.android.synthetic.main.activity_main.view.canvas

class RenderView : TextureView {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var renderer: Renderer? = null
    private var isDrawable: Boolean = false
    private var buffer: Bitmap? = null
    private var bufferCanvas: Canvas? = null
    private var driver: Driver? = null

    private val listener: TextureView.SurfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            setupRendering(width, height)
            tryStartRendering()
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) { }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return stopRendering()
        }

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            setupRendering(width, height)
            isDrawable = true
            tryStartRendering()
        }

    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        surfaceTextureListener = listener
        isOpaque = false
    }


    private fun setupRendering(width: Int, height: Int) {
        synchronized(this, {
            buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bufferCanvas = Canvas(buffer)
        })
        renderer?.setup(width, height)
    }

    private fun tryStartRendering() {
        if (isDrawable) {
            if (driver != null) {
                stopRendering()
            }
            renderer?.let { renderer ->
                driver = Driver(this, renderer, paint, renderer.getName()).apply {
                    start()
                }
            }
        }
    }

    private fun stopRendering() : Boolean {
        val hasHalted = driver?.halt() ?: false
        if (hasHalted) {
            driver = null
        }
        synchronized(this, {
            buffer = null
            bufferCanvas = null
        })
        return hasHalted
    }

    fun setRenderer(renderer: Renderer) {
        this.renderer = renderer
        if (isDrawable) {
            renderer.setup(width, height)
        }
        tryStartRendering()
    }

    class Driver(val view: RenderView, val renderer: Renderer, val paint: Paint, name: String) : Thread(name) {

        private var running = false

        override fun run() {
            while (running) {

                try {

                    // Update
                    renderer.update()

                    // Render
                    synchronized(view, {
                        view.bufferCanvas?.let {
                            it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                            renderer.render(it)
                        }
                    })

                } catch (e: Throwable) {
                    e.printStackTrace()
                }

                // Page flip
                view.lockCanvas()?.let { canvas ->
                    try {
                        view.buffer?.apply {
                            canvas.drawBitmap(this, 0f, 0f, paint)
                        }
                    } finally {
                        view.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }

        override fun start() {
            running = true
            super.start()
        }

        override fun interrupt() {
            running = false
            super.interrupt()
        }

        fun halt(): Boolean {
            running = false
            try {
                join(1000)
            } catch (e: InterruptedException) {
                return false
            }
            return true
        }
    }
}