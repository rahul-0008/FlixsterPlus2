package com.example.flixsterplus

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixsterplus.databinding.ActivityMainBinding
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import java.lang.System.console


private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val ARTICLE_SEARCH_URL =
    "https://api.themoviedb.org/3/trending/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"
fun parseFromJason(movieJasonArray: JSONArray,json: JsonHttpResponseHandler.JSON): List<Article> {
    val movies = mutableListOf<Article>()
    for (i in 0 until movieJasonArray.length()) {
        var movieJson = movieJasonArray.getJSONObject(i)
        val known = movieJson.getJSONArray("known_for")
        var name = "Blade Runner 2049"


        movies.add(
            Article(
                name,
                known.getJSONObject(0).getString("poster_path"),
                known.getJSONObject(0).getString("overview"),
                movieJson.getString("name"),
                movieJson.getString("profile_path")
            )
        )
    }
    return movies
}

class MainActivity : AppCompatActivity() {
    private val articles = mutableListOf<Article>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        articlesRecyclerView = findViewById(R.id.articles)
        val articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter

        articlesRecyclerView.layoutManager = GridLayoutManager(view.context, 2).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client[
                "https://api.themoviedb.org/3/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US",
                object : JsonHttpResponseHandler()

                {

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Successfully fetched articles")
                try{
                    val resultsJSON  = json.jsonObject.getJSONArray("results")
                    val parsed = parseFromJason(resultsJSON , json)
                    val models1 : List<Article> = parsed
                    articlesRecyclerView.adapter = ArticleAdapter( this@MainActivity,models1)

                }catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode : $response")

            }
        }]

    }
}
