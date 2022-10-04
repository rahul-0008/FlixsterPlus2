package com.example.flixsterplus

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Deatil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        var movieImageView = findViewById<ImageView>(R.id.movie_image)
        var actorImageView = findViewById<ImageView>(R.id.actor_image)

        var actor = findViewById<TextView>(R.id.name)
        var known = findViewById<TextView>(R.id.known)
        var desc = findViewById<TextView>(R.id.description)
        // TODO: Get the extra from the Intent
        val mname = intent.getStringExtra("mname")
        val aname = intent.getStringExtra("aname")
        val murl = intent.getStringExtra("murl")
        val aurl = intent.getStringExtra("aurl")
        val des = intent.getStringExtra("des")
        // TODO: Set the title, byline, and abstract information from the article
        actor.text = aname
        known.text = mname
        desc.text = des
        // TODO: Load the media image
        Glide.with(this)
            .load(murl)
            .into(movieImageView)
        Glide.with(this)
            .load(aurl)
            .into(actorImageView)
    }
}