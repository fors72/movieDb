package com.app.moviedb.view.adapter

import android.content.Context
import android.util.Pair
import com.app.moviedb.R
import com.app.moviedb.data.Movie
import com.app.moviedb.view.holder.MovieHolder

class MovieRecyclerAdapter(context:Context):SRAdapter<Movie>(context, mutableListOf()) {
    override fun getHolderType(`object`: Movie?): Pair<Class<out SRViewHolder<Movie>>, Int> {
        return Pair(MovieHolder::class.java, R.layout.movie_holder_view)
    }
}