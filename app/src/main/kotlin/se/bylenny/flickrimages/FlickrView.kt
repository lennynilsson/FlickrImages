package se.bylenny.flickrimages

interface FlickrView {
    fun setImageSrc(src: String, aspect: Float)
    fun setCaption(caption: String?)
    fun setAuthor(author: String?)
    fun removeImage()
}