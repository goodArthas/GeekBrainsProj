package ru.geekbrainsproj.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.geekbrainsproj.AppState
import ru.geekbrainsproj.MovieRecyclerAdapter
import ru.geekbrainsproj.R
import ru.geekbrainsproj.databinding.ActivityMainBinding
import ru.geekbrainsproj.model.pojo.MovieInfo
import ru.geekbrainsproj.view_model.MainViewModel

const val ADULT_CONTENT_SHOWING = "adultContent"

class MainActivity : AppCompatActivity(), MovieRecyclerAdapter.RecyclerCallback {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: MovieRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.liveDataLoading.observe(this, Observer { showLoading(it.boolean) })

        initRecycler()

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(0)?.isChecked = getAdultStateFromSharedPreferences()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onStop() {
        super.onStop()
        closeOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.adultShowState -> {
                val state = !item.isChecked
                item.isChecked = state
                manageShowingAdultContent(state)
            }
            R.id.watchLaterItem -> {
                val intent = Intent(this, WatchLaterActivity::class.java)
                startActivity(intent)
            }
            R.id.phoneBook -> {
                val intent = Intent(this, MyPhoneBookActivity::class.java)
                startActivity(intent)
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun manageShowingAdultContent(checked: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(ADULT_CONTENT_SHOWING, checked).apply()
    }

    private fun getAdultStateFromSharedPreferences(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ADULT_CONTENT_SHOWING, false)
    }

    private fun initRecycler() {
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(this, 3)
        recyclerAdapter = MovieRecyclerAdapter(emptyList(), this)
        binding.recyclerViewMovies.adapter = recyclerAdapter

        viewModel.liveData.observe(this, Observer {
            when (it) {
                is AppState.Success -> {
                    it.movieData.movieInfo?.let {
                        recyclerAdapter.setData(it)
                        binding.recyclerViewMovies.adapter = recyclerAdapter
                    }
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
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.INVISIBLE
        }

    }

    private fun showError(error: Throwable) {
        binding.recyclerViewMovies.createAndShow(R.string.smth_go_wrong)
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

    override fun movieToWatchLater(movie: MovieInfo) {
        viewModel.addFilmToWatchLater(movie)
    }

}
