package com.app.moviedb.presentation.di.component

import android.content.Context
import com.app.moviedb.MovieDBApp
import com.app.moviedb.view.activity.DetailMovieActivity
import com.app.moviedb.presentation.di.module.ApplicationModule
import com.app.moviedb.view.fragment.PopularListFragment
import com.app.moviedb.view.fragment.SearchFragment


import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: MovieDBApp)
    fun inject(app: DetailMovieActivity)
    fun inject(app: PopularListFragment)
    fun inject(app: SearchFragment)

    fun context():Context

}