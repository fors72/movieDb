package com.app.moviedb.data




data class MovieListResponse(var page:Int,var results: List<Movie>)

data class VideoListResponse(var id:Int,var results: List<Video>)