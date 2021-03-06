package com.manzooralam.devfest_2020.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import ccom.manzooralam.devfest_2020ui.util.MainViewModelFactory
import com.manzooralam.devfest_2020.data.MovieRepositoryImpl
import com.manzooralam.devfest_2020.data.local.database.MovieDatabase
import com.manzooralam.devfest_2020.data.local.database.entity.Movie
import com.manzooralam.devfest_2020.data.remote.retrofit.RetrofitBuilder
import com.manzooralam.devfest_2020.R
import com.manzooralam.devfest_2020.ui.adapter.MoviesAdapter
import com.devfest.india.bmsclone.util.NetworkHelper
import com.manzooralam.devfest_2020.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        observeViewModel()
    }

    private fun setupViewModel() {
        showProgress()

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(
                NetworkHelper(this),
                MovieRepositoryImpl(
                    MovieDatabase.getInstance(this).movieDao(),
                    RetrofitBuilder.buildService()
                )
            )
        )[MainViewModel::class.java]
        viewModel.onCreate()
    }

    private fun observeViewModel() {
        viewModel.movieResponse.observe(this, Observer {
            showMovies(it.results)
            hideProgress()
        })

        viewModel.errorResponse.observe(this, Observer {
            showErrorMessage(it)
            hideProgress()
        })
    }

    private fun showMovies(movies: List<Movie>) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = MoviesAdapter(movies)
    }

    private fun showErrorMessage(errorMessage: String?) {
        errorView.visibility = View.VISIBLE
        errorView.text = errorMessage
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }
}