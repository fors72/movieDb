package com.app.moviedb.view.holder

import android.view.View
import com.app.moviedb.data.Movie
import com.app.moviedb.presentation.navigation.Navigator
import com.app.moviedb.view.adapter.SRAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_holder_view.view.*

class MovieHolder(val view: View,adapter:SRAdapter<Movie>):SRAdapter.SRViewHolder<Movie>(view,adapter) {

    override fun bindHolder(movie: Movie) {
        view.apply {
            Picasso.get().load("http://image.tmdb.org/t/p/w185${movie.poster_path}").into(posterImage)
            movieName.text = movie.title
            ratingText.text = movie.vote_average
            setOnClickListener {
                Navigator.navigateToDetailMovie(adapter.context,movie.idMov)
            }
        }

    }
}