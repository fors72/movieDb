package com.app.moviedb.data.repository.datasource

import com.app.moviedb.data.Movie
import com.app.moviedb.data.MovieListResponse
import com.app.moviedb.data.Video
import com.app.moviedb.data.VideoListResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.LinkedHashMap
import javax.inject.Inject

class ApiMovieDataSource @Inject constructor():MovieDataSource {

    override fun getVideos(id: Int): Observable<List<Video>> {
        return api.getVideos(id.toString(),defaultParams).map { it.results }
    }

    private val api: MovieDbApi
    private val defaultParams = linkedMapOf(
            "api_key" to "191d94185dac0ef7cd131600c038c2e2",
            "language" to "en-US"
    )

    init {
        api = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiMovieDataSource.MovieDbApi::class.java)
    }

    override fun searchMovies(page: Int, q: String): Observable<List<Movie>> {
        val param = LinkedHashMap(defaultParams)
        param["page"] = page.toString()
        param["query"] = q
        return api.searchMovies(param).map { it.results }
    }

    override fun getMovies(page: Int): Observable<List<Movie>> {
        val requestPage = if (page == -1){
            1
        }else{
            page
        }
        val param = LinkedHashMap(defaultParams)
        param["page"] = requestPage.toString()
        return api.getMovies(param).map { it.results }
    }

    override fun getMovie(id: Int): Observable<Movie> {
        return api.getMovie(id.toString(),defaultParams)
    }

    interface MovieDbApi{

        @GET("movie/popular")
        fun getMovies(@QueryMap map: Map<String, String>): Observable<MovieListResponse>

        @GET("search/movie")
        fun searchMovies(@QueryMap map: Map<String, String>): Observable<MovieListResponse>

        @GET("movie/{movie_id}")
        fun getMovie(@Path("movie_id") movie_id:String, @QueryMap map: Map<String, String>): Observable<Movie>

        @GET("movie/{movie_id}/videos")
        fun getVideos(@Path("movie_id") movie_id:String, @QueryMap map: Map<String, String>): Observable<VideoListResponse>

    }
}