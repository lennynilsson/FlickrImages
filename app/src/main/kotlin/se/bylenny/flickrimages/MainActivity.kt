package se.bylenny.flickrimages

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import se.bylenny.flickrimages.flickr.FlickrApi
import se.bylenny.flickrimages.flickr.FlickrApiFactory
import se.bylenny.flickrimages.flickr.model.Photo


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FlickrAdapter
    private lateinit var flickrApi: FlickrApi
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        list.layoutManager = LinearLayoutManager(this)
        flickrApi = FlickrApiFactory.create(cacheDir)
    }

    override fun onResume() {
        super.onResume()

        searchField.setOnEditorActionListener { view, actionId, event ->
            val done = actionId == EditorInfo.IME_ACTION_DONE
                    || (event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            if (done) {
                load()
            }
            done
        }

        refreshLayout.setOnRefreshListener { load() }

        this.adapter = FlickrAdapter(Picasso.with(this))
        list.adapter = adapter

        load()
    }

    override fun onPause() {
        super.onPause()
        list.adapter = null
    }

    private fun createSearch(query: String, page: Int = 1, pageSize: Int = 500): Map<String, String> = mapOf(
            "method" to "flickr.photos.search",
            "api_key" to BuildConfig.FLICKR_KEY,
            "text" to query,
            "sort" to "interestingness-desc",
            "extras" to "description, license, owner_name, tags, o_dims, views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o",
            "privacy_filter" to "1",
            "safe_search" to "2",
            "content_type" to "1",
            "media" to "photos",
            "per_page" to "$pageSize",
            "photos" to "$page",
            "format" to "json",
            "nojsoncallback" to "1"
    )

    private fun load() {
        val query = searchField.editableText.toString()
        Log.d(FlickrAdapter.TAG, "Searching for \"$query\"")
        refreshLayout.isRefreshing = true
        subscription?.dispose()
        subscription = flickrApi.search(createSearch(query, 1, 50))
                .doOnNext {
                    if (it.stat == "ok") {
                        Log.d(FlickrAdapter.TAG, "Received list of ${it?.photos?.perpage ?: 0} images ${it?.photos?.photo}")
                    } else {
                        Log.e(FlickrAdapter.TAG, "Received status ${it.stat}")
                    }
                }
                .map { it.photos?.photo ?: emptyList() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { refreshLayout.isRefreshing = false }
                .subscribe({ photos: List<Photo> ->
                    adapter.load(photos)
                    list.scrollToPosition(0)
                }, {
                    Log.e(FlickrAdapter.TAG, "Unable to fetch image list", it)
                })
    }
}

