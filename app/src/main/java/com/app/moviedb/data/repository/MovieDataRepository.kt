package com.app.moviedb.data.repository

import com.app.moviedb.data.Movie
import com.app.moviedb.data.Video
import com.app.moviedb.data.repository.datasource.ApiMovieDataSource
import com.app.moviedb.data.repository.datasource.DBMovieDataSource
import com.app.moviedb.data.repository.datasource.MemoryMovieDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataRepository @Inject constructor(private val memoryMovieDataSource: MemoryMovieDataSource,
                                              private val apiMovieDataSource: ApiMovieDataSource,
                                              private val dbMovieDataSource: DBMovieDataSource):MovieRepository {
    override fun getVideos(id: Int): Observable<List<Video>> {
        return memoryMovieDataSource.getVideos(id)
                .onErrorResumeNext(subscribeToIo(apiMovieDataSource.getVideos(id)
                        .map { memoryMovieDataSource.cacheVideo(id,it) }))
    }

    override fun searchMovies(page: Int, q: String): Observable<List<Movie>> {
        return subscribeToIo(apiMovieDataSource.searchMovies(page, q))
    }


    override fun getMovies(page: Int): Observable<List<Movie>> = memoryMovieDataSource.getMovies(page)
            .onErrorResumeNext(subscribeToIo(apiMovieDataSource.getMovies(page)
                    .doOnNext { dbMovieDataSource.setPage(page,it) })
                    .map { memoryMovieDataSource.cachePage(page,it) })
            .onErrorResumeNext(dbMovieDataSource.getMovies(page))

    override fun getMovie(id: Int): Observable<Movie> = memoryMovieDataSource.getMovie(id)
            .onErrorResumeNext(dbMovieDataSource.getMovie(id))
            .onErrorResumeNext(subscribeToIo(apiMovieDataSource.getMovie(id).doOnNext { /*save movie to DB*/ })) //todo


    private fun<T> subscribeToIo(observable: Observable<T>):Observable<T> = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}