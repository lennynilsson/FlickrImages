package se.bylenny.flickrimages.flickr

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import se.bylenny.flickrimages.flickr.model.FlickrResp

interface FlickrApi {
    @GET("/services/rest/")
    fun search(@QueryMap query: Map<String, String>): Observable<FlickrResp>
}
