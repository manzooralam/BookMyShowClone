package com.manzooralam.devfest_2020.data.local.database.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Manzoor
 */
data class Movie(
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
)