package com.app.moviedb.presentation.di.module

import com.app.moviedb.data.repository.datasource.ApiMovieDataSource
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
class ApiModule {


    @Provides
    @Singleton
    fun provideApi():ApiMovieDataSource.MovieDbApi = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ApiMovieDataSource.MovieDbApi::class.java)

}