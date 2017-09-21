package se.bylenny.flickrimages.flickr.model

import java.io.Serializable
import com.squareup.moshi.Json

class Photos : Serializable {
    @Json(name = "photos")
    var photos: Int = -1
    @Json(name = "pages")
    var pages: String = ""
    @Json(name = "perpage")
    var perpage: String = ""
    @Json(name = "total")
    var total: String = ""
    @Json(name = "photo")
    var photo: List<Photo> = emptyList()
}
