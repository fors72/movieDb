package com.app.moviedb.view.inter

import com.app.moviedb.data.Movie
import com.app.moviedb.view.inter.base.VideoView
import com.app.moviedb.view.inter.base.LoadView

interface DetailMovieView: LoadView,VideoView {

    fun renderMovie(movie: Movie)

}