package se.bylenny.flickrimages

import android.media.Image
import android.net.Uri
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.flickr_list_item.view.*
import se.bylenny.flickrimages.extensions.textToggle


class FlickrViewHolder(
        parent: ViewGroup,
        @LayoutRes layoutRes: Int
): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
), FlickrView {

    override fun setImageSrc(src: String, aspect: Float) {
        val imageView = itemView.image
        imageView.setImage(src, aspect)

        Picasso.with(itemView.context)
                .load(Uri.parse(src))
                .placeholder(R.drawable.placeholder)
                .into(imageView as ImageView)
    }

    override fun removeImage() {
        itemView.image.setImage("", 0f)
    }

    override fun setCaption(caption: String?) {
        itemView.caption.textToggle(caption)
    }

    override fun setAuthor(authorName: String?) {
        itemView.author.textToggle(authorName)
    }

}