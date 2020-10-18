package com.manzooralam.devfest_2020.data.remote.retrofit

import com.manzooralam.devfest_2020.data.local.database.entity.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Manzoor
 */
interface MovieService {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") key: String): Call<MovieResponse>
}