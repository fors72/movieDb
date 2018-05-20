package com.app.moviedb.presentation.presenter

import java.lang.ref.WeakReference



abstract class BasePresenter<V> {


    protected var view: WeakReference<V?>? = null



    fun attachView(view: V){
        this.view = WeakReference(view)
        if (setupDone()) {
            updateView()
        }

    }

    fun detachView(){
        view = null
    }

    protected fun view(): V? {
        return if (view == null) {
            null
        } else {
            view!!.get()
        }
    }

    protected abstract fun updateView()



    private fun setupDone(): Boolean {
        return view() != null
    }

}