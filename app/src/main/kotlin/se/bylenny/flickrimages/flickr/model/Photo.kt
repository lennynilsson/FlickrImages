package se.bylenny.flickrimages.flickr.model

import java.io.Serializable
import com.squareup.moshi.Json

class Photo : Serializable {
    @Json(name = "id")
    var id: String = ""
    @Json(name = "owner")
    var owner: String = ""
    @Json(name = "title")
    var title: String = ""
    @Json(name = "license")
    var license: Int? = null
    @Json(name = "ownername")
    var ownername: String = ""
    @Json(name = "views")
    var views: String = ""
    @Json(name = "tags")
    var tags: String = ""
    @Json(name = "media")
    var media: String = ""
    @Json(name = "media_status")
    var media_status: String = ""
    @Json(name = "url_sq")
    var url_sq: String = ""
    @Json(name = "height_sq")
    var height_sq: Int? = null
    @Json(name = "width_sq")
    var width_sq: Int? = null
    @Json(name = "url_t")
    var url_t: String = ""
    @Json(name = "height_t")
    var height_t: Int? = null
    @Json(name = "width_t")
    var width_t: Int? = null
    @Json(name = "url_s")
    var url_s: String = ""
    @Json(name = "height_s")
    var height_s: String = ""
    @Json(name = "width_s")
    var width_s: String = ""
    @Json(name = "url_q")
    var url_q: String = ""
    @Json(name = "height_q")
    var height_q: String = ""
    @Json(name = "width_q")
    var width_q: String = ""
    @Json(name = "url_m")
    var url_m: String = ""
    @Json(name = "height_m")
    var height_m: String = ""
    @Json(name = "width_m")
    var width_m: String = ""
    @Json(name = "url_n")
    var url_n: String = ""
    @Json(name = "height_n")
    var height_n: String = ""
    @Json(name = "width_n")
    var width_n: String = ""
    @Json(name = "url_z")
    var url_z: String = ""
    @Json(name = "height_z")
    var height_z: String = ""
    @Json(name = "width_z")
    var width_z: String = ""
    @Json(name = "url_c")
    var url_c: String = ""
    @Json(name = "height_c")
    var height_c: String = ""
    @Json(name = "width_c")
    var width_c: String = ""
    @Json(name = "url_l")
    var url_l: String = ""
    @Json(name = "height_l")
    var height_l: String = ""
    @Json(name = "width_l")
    var width_l: String = ""
    @Json(name = "pathalias")
    var pathalias: String = ""
    @Json(name = "url_o")
    var url_o: String = ""
    @Json(name = "height_o")
    var height_o: String = ""
    @Json(name = "width_o")
    var width_o: String = ""

    override fun toString(): String {
        return "Photo(url_sq='$url_sq', height_sq=$height_sq, width_sq=$width_sq)"
    }


}
