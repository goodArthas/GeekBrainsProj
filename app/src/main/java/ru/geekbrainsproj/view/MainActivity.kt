package ru.geekbrainsproj.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.geekbrainsproj.AppState
import ru.geekbrainsproj.MovieRecyclerAdapter
import ru.geekbrainsproj.R
import ru.geekbrainsproj.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: MovieRecyclerAdapter

    private lateinit var progressBarLoadingMovie: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getLiveDataLoading().observe(this, Observer { showLoading(it.boolean) })

        initViewElements()
        initRecycler()

    }

    private fun initViewElements() {
        progressBarLoadingMovie = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerViewMovies)
    }

    private fun initRecycler() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        viewModel.getLiveData().observe(this, Observer {
            when (it) {
                is AppState.Success -> {
                    Log.d("QWE", "AppState.Success: initRecycler" + it.movieArray.size)
                    recyclerAdapter = MovieRecyclerAdapter(it.movieArray)
                    recyclerView.adapter = recyclerAdapter
                }
                is AppState.Error -> showError(it.error)
                else -> {
                    showError(ArrayIndexOutOfBoundsException())
                }
            }
        })

    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBarLoadingMovie.visibility = View.VISIBLE
            false -> progressBarLoadingMovie.visibility = View.INVISIBLE
        }

    }

    private fun showError(error: Throwable) {
        recyclerView.createAndShow(R.string.smth_go_wrong)
        when (error) {
            is NullPointerException -> Toast.makeText(applicationContext, getString(R.string.data_not_loaded), Toast.LENGTH_SHORT).show()
            else -> {
                Toast.makeText(applicationContext, getString(R.string.smth_go_wrong), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun View.createAndShow(resId: Int, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(this, getString(resId), length).show()
    }
}
