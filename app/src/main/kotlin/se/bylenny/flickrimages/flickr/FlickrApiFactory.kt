package se.bylenny.flickrimages.flickr

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File


class FlickrApiFactory {
    companion object {
        fun create(cacheDir: File): FlickrApi
            = Retrofit.Builder()
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(LoggingInterceptor())
                        .cache(Cache(cacheDir, Int.MAX_VALUE.toLong()))
                        .build()
                )
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create<FlickrApi>(FlickrApi::class.java)
    }
}