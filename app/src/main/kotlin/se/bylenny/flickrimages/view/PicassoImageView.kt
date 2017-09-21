package se.bylenny.flickrimages.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class PicassoImageView(context: Context, attrs: AttributeSet? = null): ImageView(context, attrs), Target {

    companion object {
        val TAG = "PicassoImageView"
    }

    private var aspect: Float = 9f.div(16f)
    private var src: String = ""

    fun setImage(src: String, ratio: Float) {
        this.src = src
        aspect = ratio
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
        setImageDrawable(placeHolderDrawable)
    }

    override fun onBitmapFailed(errorDrawable: Drawable) {
        setImageDrawable(errorDrawable)
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        setImageBitmap(bitmap)
        invalidate()
        Log.d(TAG, "Image loaded: ".plus(from.name))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = width.toFloat().times(aspect).toInt()
        super.onMeasure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
    }
}