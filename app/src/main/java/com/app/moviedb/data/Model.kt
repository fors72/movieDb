package com.app.moviedb.data

import com.app.moviedb.data.database.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.annotation.Unique


@Table(database = AppDatabase::class)
data class Movie(
        @PrimaryKey(autoincrement = true) var dbId:Long? = null,
        @SerializedName("id")
        @Column var idMov :Int = 0,
        @Column var title :String? = null,
        @Column var poster_path :String? = null,
        @Column var overview :String? = null,
        @Column var release_date :String? = null,
        @Column var vote_average :String? = null,
        @Column var backdrop_path :String? = null
)


data class Video(
        var id :String? = null,
        var name :String? = null,
        var key:String
)