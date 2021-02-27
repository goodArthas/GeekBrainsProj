package ru.geekbrainsproj.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ru.geekbrainsproj.databinding.DetailMovieActivityBinding
import ru.geekbrainsproj.view_model.MainViewModel
import ru.geekbrainsproj.view_model.MainViewModel.Companion.POSTER_FILM

class DetailMovieActivity : AppCompatActivity() {

    lateinit var binding: DetailMovieActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DetailMovieActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        fillViewsWithData()
    }


    private fun fillViewsWithData() {
        val getIntent = intent
        binding.txtViewFilmName.text = getIntent.getStringExtra(MainViewModel.NAME_FILM)
        binding.txtViewDescriptionFilm.text = getIntent.getStringExtra(MainViewModel.DESCRIBE_FILM)

        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/original${getIntent.getStringExtra(POSTER_FILM)}")
                .resize(180, 280)
                .centerCrop()
                .into(binding.imageViewPoster)
    }

}