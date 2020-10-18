package com.manzooralam.devfest_2020

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devfest.india.bmsclone.data.MovieRepository
import com.manzooralam.devfest_2020.data.local.database.entity.MovieResponse
import com.devfest.india.bmsclone.util.NetworkHelper


class MainViewModel(
    private val networkHelper: NetworkHelper,
    private val movieRepository: MovieRepository
) : ViewModel() {

    companion object {
        private const val API_KEY = "db10d60400cb284f515f0a646f8943b8"
        private const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

    private val _movieResponse = MutableLiveData<MovieResponse>()
    val movieResponse: LiveData<MovieResponse>
        get() = _movieResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _errorResponse

    fun onCreate() {
        if (networkHelper.isNetworkConnected()) {
            movieRepository.fetchMovies(API_KEY, { movieResponse ->
                _movieResponse.postValue(movieResponse)
            }, { error ->
                _errorResponse.postValue(error)
            })
        } else {
            movieRepository.getMoviesLocal { movieResponse ->
                if (movieResponse != null && movieResponse.results.isNotEmpty()) {
                    _movieResponse.postValue(movieResponse)
                } else {
                    _errorResponse.postValue(SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}