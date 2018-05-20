package com.app.moviedb.view.holder

import android.view.View
import com.app.moviedb.data.Video
import com.app.moviedb.view.adapter.SRAdapter
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_holder_view.view.*
import org.jetbrains.anko.browse

class VideoHolder(val view: View, adapter: SRAdapter<Video>): SRAdapter.SRViewHolder<Video>(view,adapter) {
    override fun bindHolder(video: Video) {
        view.apply {
            Picasso.get().load("http://img.youtube.com/vi/${video.key}/0.jpg")
                    .networkPolicy(NetworkPolicy.NO_STORE)
                    .into(videoImage)
            videoName.text = video.name
            setOnClickListener {
                adapter.context.browse("https://www.youtube.com/watch?v=${video.key}")
            }
        }
    }
}