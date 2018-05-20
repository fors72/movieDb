package com.app.moviedb.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.app.moviedb.R
import com.app.moviedb.data.Movie
import com.app.moviedb.data.Video
import com.app.moviedb.presentation.di.Injector
import com.app.moviedb.presentation.presenter.DetailMoviePresenter
import com.app.moviedb.view.adapter.VideoAdapter
import com.app.moviedb.view.inter.DetailMovieView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.jetbrains.anko.alert
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity(),DetailMovieView {
    override fun showLoadVideo() {
        videoProgress.visibility = View.VISIBLE
    }

    override fun hideLoadVideo() {
        videoProgress.visibility = View.GONE
    }


    @Inject
    lateinit var detailMoviePresenter:DetailMoviePresenter

    lateinit var adapter:VideoAdapter

    override fun renderMovie(movie: Movie) {
        toolbar_layout.title = movie.title
        description.text = movie.overview
        releaseDateText.text = movie.release_date
        ratingText.text = movie.vote_average
        Picasso.get().load("http://image.tmdb.org/t/p/w500${movie.backdrop_path}").into(backdropImage)
        Picasso.get().load("http://image.tmdb.org/t/p/w185${movie.poster_path}").into(moviePoster)
    }

    override fun showLoading(message: String) {}

    override fun hideLoading() {}

    override fun showError(message: String) {
        alert(message){
            positiveButton("OK"){dismiss()}
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = VideoAdapter(this)
        videoRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        videoRecycler.adapter = adapter
        videoRecycler.isNestedScrollingEnabled = false
        detailMoviePresenter.attachView(this)
        detailMoviePresenter.init(intent.getIntExtra("movieId",-1))
        nested.fullScroll(View.FOCUS_UP)
    }

    override fun renderVideos(video: List<Video>) {
        adapter.list = video
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }
}
