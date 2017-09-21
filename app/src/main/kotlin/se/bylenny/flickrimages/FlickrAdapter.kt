package se.bylenny.flickrimages

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import se.bylenny.flickrimages.flickr.FlickrApiFactory
import se.bylenny.flickrimages.flickr.model.Photo
import java.io.File

class FlickrAdapter(private val picasso: Picasso) : RecyclerView.Adapter<FlickrViewHolder>() {
    companion object {
        val TAG = "FlickrAdapter"
    }
    private var list: List<Photo> = emptyList()
    private val decorator = FlickDecorator()

    fun load(photos: List<Photo>) {
        Log.d(TAG, "Loaded ${photos.size} images")
        list = photos
        notifyDataSetChanged()
    }

    private fun preFetch(list: List<Photo>) {
        list.forEach { picasso.load(it.url_m).fetch() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder
        = FlickrViewHolder(parent, R.layout.flickr_list_item)

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        decorator.decorate(holder, list[position])
    }

    override fun onViewDetachedFromWindow(holder: FlickrViewHolder) {
        decorator.unbind(holder)
        super.onViewDetachedFromWindow(holder)
    }
    override fun getItemCount(): Int = list.size

}