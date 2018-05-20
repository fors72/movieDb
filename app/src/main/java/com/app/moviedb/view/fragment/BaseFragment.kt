package com.app.moviedb.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment:Fragment() {

    init {
        retainInstance = true
    }
    abstract fun nameOfFragment():String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpToolBar(nameOfFragment())
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpToolBar(title: String) {
        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        if (actionBar != null) {
            actionBar.title = title
        }
    }

}