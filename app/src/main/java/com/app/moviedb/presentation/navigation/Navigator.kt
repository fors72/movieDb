package com.app.moviedb.presentation.navigation

import android.content.Context
import com.app.moviedb.view.activity.DetailMovieActivity
import org.jetbrains.anko.startActivity

object Navigator {


    fun navigateToDetailMovie(context:Context,movieId:Int){
        context.startActivity<DetailMovieActivity>("movieId" to movieId)
    }

}