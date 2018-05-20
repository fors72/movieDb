package com.app.moviedb.presentation.presenter

import com.app.moviedb.data.repository.MovieDataRepository
import com.app.moviedb.view.inter.DetailMovieView
import javax.inject.Inject

class DetailMoviePresenter
@Inject
constructor(private val movieDataRepository: MovieDataRepository): BasePresenter<DetailMovieView>() {



    fun init(movieId:Int) {
        initMovieDetail(movieId)
        initVideo(movieId)
    }

    fun initMovieDetail(movieId:Int){
        movieDataRepository.getMovie(movieId).subscribe({
            view()?.renderMovie(it)
        }, {
            view()?.showError(it?.message ?: "error")
        }, {})
    }


    fun initVideo(movieId:Int){
        view()?.showLoadVideo()
        movieDataRepository.getVideos(movieId).subscribe({
            view()?.renderVideos(it)
        }, {
            view()?.hideLoadVideo()
        }, {
            view()?.hideLoadVideo()
        })
    }

    override fun updateView() {
    }

}