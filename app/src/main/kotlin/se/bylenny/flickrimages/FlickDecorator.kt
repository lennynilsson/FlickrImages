package se.bylenny.flickrimages

import android.util.Log
import se.bylenny.flickrimages.flickr.model.Photo

class FlickDecorator {
    companion object {
        val TAG = "FlickDecorator"
    }
    fun decorate(view: FlickrView, item: Photo) {
        if (item.url_m.isNotBlank()) {
            Log.d(TAG, "decorating ${item.url_m}")
            val aspect = item.height_m.toFloat().div(item.width_m.toFloat())
            view.setImageSrc(item.url_m, aspect)
        }
        view.setCaption(item.title)
        view.setAuthor(item.ownername)
    }

    fun unbind(view: FlickrView) {
        view.removeImage()
    }
}