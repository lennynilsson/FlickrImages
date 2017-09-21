package se.bylenny.flickrimages.flickr.model

import com.squareup.moshi.Json
import java.io.Serializable

class Description : Serializable {
    @Json(name = "_content")
    var content: String = ""
}