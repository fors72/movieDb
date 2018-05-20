package com.app.moviedb.presentation.presenter

import com.app.moviedb.data.repository.MovieDataRepository
import com.app.moviedb.view.inter.MovieListView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviePresenter
@Inject
constructor(private val movieDataRepository: MovieDataRepository): BasePresenter<MovieListView>() {

    var currentPage = 1

    var isLoading = false



    private fun init() {
        if (isLoading) return
        isLoading = true
        view()?.showLoading("")
        movieDataRepository.getMovies(-1).subscribe({
            view()?.renderObject(it)
        }, {
            view()?.showError(it?.message ?: "error")
            isLoading = false
            view()?.hideLoading()
        }, {
            isLoading = false
            view()?.hideLoading()
        })
    }

    fun next() {
        if (isLoading) return
        currentPage++
        isLoading = true
        movieDataRepository.getMovies(currentPage).subscribe({
            view()?.renderObject(it)
        }, {
//            view()?.showError(it?.message ?: "error")
            isLoading = false
        }, {
            isLoading = false
        })
    }


    override fun updateView() {
        init()
    }



}