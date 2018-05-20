package com.app.moviedb.view.adapter

import android.content.Context
import android.util.Pair
import com.app.moviedb.R
import com.app.moviedb.data.Video
import com.app.moviedb.view.holder.VideoHolder

class VideoAdapter(context: Context):SRAdapter<Video>(context, mutableListOf()) {
    override fun getHolderType(`object`: Video?): Pair<Class<out SRViewHolder<Video>>, Int> {
        return Pair(VideoHolder::class.java, R.layout.video_holder_view)
    }
}