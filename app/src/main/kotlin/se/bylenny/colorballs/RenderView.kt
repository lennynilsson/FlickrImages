package se.bylenny.colorballs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.SurfaceTexture
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.TextureView
import se.bylenny.colorballs.interfaces.Controller
import se.bylenny.colorballs.interfaces.Renderer

class RenderView : TextureView {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var renderer: Renderer? = null
    private var controller: Controller? = null
    private var isDrawable: Boolean = false
    private var buffer: Bitmap? = null
    private var bufferCanvas: Canvas? = null
    private var driver: Driver? = null
    private var pointers: Map<Int, Pointer> = emptyMap()

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

    override fun onTouchEvent(event: MotionEvent): Boolean = controller?.let {
        updatePointers(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_BUTTON_PRESS -> it.onButtonPress(pointers)
            MotionEvent.ACTION_BUTTON_RELEASE -> it.onButtonRelease(pointers)
            MotionEvent.ACTION_DOWN -> it.onPress(pointers)
            MotionEvent.ACTION_UP -> it.onRelease(pointers)
            MotionEvent.ACTION_MOVE -> it.onMove(pointers)
            MotionEvent.ACTION_CANCEL -> it.onCancel(pointers)
            MotionEvent.ACTION_OUTSIDE -> it.onOutside(pointers)
            else -> super.onTouchEvent(event)
        }
    } ?: super.onTouchEvent(event)

    private fun updatePointers(event: MotionEvent) {
        pointers = (0..event.pointerCount).asSequence().map { index ->
            val pointerId = event.getPointerId(index)
            (pointers.get(pointerId) ?: Pointer()).apply {
                id = pointerId
                x = event.getX(index)
                y = event.getY(index)
                preasure = event.getPressure(index)
                time = event.downTime
                tool = event.getPointerTool(index)
                button = event.getButton()
            }
        }.associateBy { it.id }
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

    fun setRenderer(renderer: Renderer?) {
        this.renderer = renderer
        if (isDrawable && renderer != null) {
            renderer.setup(width, height)
        }
        tryStartRendering()
    }

    fun setController(controller: Controller?) {
        this.controller = controller
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

private fun MotionEvent.getButton(): Pointer.Button = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    when (actionButton) {
        MotionEvent.BUTTON_BACK -> Pointer.Button.BACK
        MotionEvent.BUTTON_FORWARD  -> Pointer.Button.FORWARD
        MotionEvent.BUTTON_PRIMARY -> Pointer.Button.PRIMARY
        MotionEvent.BUTTON_SECONDARY -> Pointer.Button.SECONDARY
        MotionEvent.BUTTON_STYLUS_PRIMARY  -> Pointer.Button.STYLUS_PRIMARY
        MotionEvent.BUTTON_STYLUS_SECONDARY  -> Pointer.Button.STYLUS_SECONDARY
        MotionEvent.BUTTON_TERTIARY -> Pointer.Button.TERTIARY
        else -> Pointer.Button.UNKNOWN
    }
} else Pointer.Button.UNKNOWN

private fun MotionEvent.getPointerTool(index: Int): Pointer.Tool = when(getToolType(index)) {
    MotionEvent.TOOL_TYPE_FINGER -> Pointer.Tool.FINGER
    MotionEvent.TOOL_TYPE_ERASER -> Pointer.Tool.ERASER
    MotionEvent.TOOL_TYPE_MOUSE -> Pointer.Tool.MOUSE
    MotionEvent.TOOL_TYPE_STYLUS -> Pointer.Tool.STYLUS
    else -> Pointer.Tool.UNKNOWN
}
