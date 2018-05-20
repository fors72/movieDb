package com.app.moviedb.data.repository

import com.app.moviedb.data.Movie
import com.app.moviedb.data.Video
import io.reactivex.Observable

interface MovieRepository {


    fun getMovies(page:Int):Observable<List<Movie>>

    fun searchMovies(page:Int,q:String):Observable<List<Movie>>

    fun getMovie(id: Int):Observable<Movie>


    fun getVideos(id: Int): Observable<List<Video>>
}