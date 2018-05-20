package com.app.moviedb.data.repository.datasource

import com.app.moviedb.data.Movie
import com.app.moviedb.data.Movie_Table
import com.app.moviedb.data.Video
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import com.raizlabs.android.dbflow.sql.language.SQLOperator
import io.reactivex.Observable
import javax.inject.Inject

class DBMovieDataSource @Inject constructor():MovieDataSource {
    override fun getVideos(id: Int): Observable<List<Video>> {
        return Observable.error(UnsupportedOperationException(MemoryMovieDataSource::class.java.name))
    }


    override fun searchMovies(page: Int, q: String): Observable<List<Movie>> {
        return Observable.error(UnsupportedOperationException(MemoryMovieDataSource::class.java.name))
    }

    override fun getMovies(page: Int): Observable<List<Movie>> {
        return if (page == -1){
            select.from(Movie::class.java).limit(20).rx().list.toObservable()
        }else{
            Observable.error(UnsupportedOperationException(DBMovieDataSource::class.java.name))
        }
    }

    override fun getMovie(id: Int): Observable<Movie> {
        return select.from(Movie::class.java)
                .where(Movie_Table.idMov `is` id)
                .rx()
                .list
                .toObservable()
                .map { it.first() }
    }

    fun setPage(page: Int,list: List<Movie>){
        if (page == -1) {
            val  model = modelAdapter<Movie>()
            select.from(Movie::class.java).rx().list.toObservable().doOnNext{ it.forEach { model.delete(it) }}
            model.saveAll(list)
        }
    }
}