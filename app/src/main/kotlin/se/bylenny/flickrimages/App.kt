package se.bylenny.flickrimages

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Picasso.setSingletonInstance(
                Picasso.Builder(this)
                        .downloader(OkHttp3Downloader(OkHttpClient.Builder()
                                .cache(Cache(cacheDir, Int.MAX_VALUE.toLong()))
                                .build()))
                        .loggingEnabled(BuildConfig.DEBUG)
                        .indicatorsEnabled(BuildConfig.DEBUG)
                        .build()
        )
    }
}