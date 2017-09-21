package se.bylenny.flickrimages.flickr.model

import java.io.Serializable
import com.squareup.moshi.Json

class FlickrResp : Serializable {
    @Json(name = "photos")
    var photos: Photos? = null
    @Json(name = "stat")
    var stat: String = ""
}
