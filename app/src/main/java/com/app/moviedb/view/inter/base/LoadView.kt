package com.app.moviedb.view.inter.base


interface LoadView {


    fun showLoading(message: String)

    fun hideLoading()

    fun showError(message: String)

}