package com.app.moviedb.view.inter.base

import com.app.moviedb.data.Video

interface VideoView {

    fun renderVideos(video: List<Video>)

    fun showLoadVideo()

    fun hideLoadVideo()

}