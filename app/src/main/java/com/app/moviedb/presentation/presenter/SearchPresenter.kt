package com.app.moviedb.presentation.presenter

import com.app.moviedb.data.repository.MovieDataRepository
import com.app.moviedb.view.inter.MovieListView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPresenter
@Inject
constructor(private val movieDataRepository: MovieDataRepository): BasePresenter<MovieListView>(){

    var query:String? = null
    var currentPage = 1
    var isLoading = false


    fun init(q:String){
        if (isLoading) return
        currentPage = 1
        query = q
        isLoading = true
        view()?.showLoading("")
        view()?.renderObject(mutableListOf())
        movieDataRepository.searchMovies(1,q).subscribe({
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

    fun next(){
        if (isLoading || query == null) return
        currentPage++
        isLoading = true
        movieDataRepository.searchMovies(currentPage,query!!).subscribe({
            view()?.addObjects(it)
        }, {
            view()?.showError(it?.message ?: "error")
            isLoading = false
        }, {
            isLoading = false
        })
    }


    override fun updateView() {
        view()?.hideLoading()
    }

}