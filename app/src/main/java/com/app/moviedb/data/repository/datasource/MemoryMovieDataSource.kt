package com.app.moviedb.data.repository.datasource

import com.app.moviedb.data.Movie
import com.app.moviedb.data.Video
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

class MemoryMovieDataSource @Inject constructor():MovieDataSource {


    private val movies:MutableList<Movie> = mutableListOf()

    private var videoOfMovieId:Int = 0
    private var video:List<Video>? = null



    override fun searchMovies(page: Int, q: String): Observable<List<Movie>> {
        return Observable.error(UnsupportedOperationException(MemoryMovieDataSource::class.java.name))
    }

    override fun getMovie(id: Int): Observable<Movie> {
        movies.forEach {
            if (it.idMov == id)return Observable.just(it)
        }
        return Observable.error(UnsupportedOperationException(MemoryMovieDataSource::class.java.name))
    }

    override fun getMovies(page: Int): Observable<List<Movie>> {
        return if (page == -1 && movies.isNotEmpty()){
            Observable.just(movies)
        }else{
            Observable.error(UnsupportedOperationException(MemoryMovieDataSource::class.java.name))
        }
    }


    fun cachePage(page: Int,list: List<Movie>):List<Movie>{
        movies.addAll(list)
        return movies
    }


    override fun getVideos(id: Int): Observable<List<Video>> {
        return if (videoOfMovieId == id && video != null){
            Observable.just(video)
        }else{
            Observable.error(UnsupportedOperationException(MemoryMovieDataSource::class.java.name))
        }
    }
    fun cacheVideo(id: Int,list: List<Video>):List<Video>{
        video = list
        videoOfMovieId = id
        return video as List<Video>
    }
}