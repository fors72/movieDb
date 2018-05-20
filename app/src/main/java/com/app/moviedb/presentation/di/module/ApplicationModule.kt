package com.app.moviedb.presentation.di.module

import android.content.Context
import com.app.moviedb.MovieDBApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val context: MovieDBApp) {

    @Provides @Singleton fun provideContext():Context = context

}