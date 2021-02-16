package ru.geekbrainsproj.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.squareup.picasso.Picasso
import ru.geekbrainsproj.R
import ru.geekbrainsproj.view_model.MainViewModel
import ru.geekbrainsproj.view_model.MainViewModel.Companion.POSTER_FILM

class DetailMovieActivity : AppCompatActivity() {


    lateinit var nameFilmTxtView: AppCompatTextView
    lateinit var describeFilmTxtView: AppCompatTextView
    lateinit var posterImgView: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_movie_activity)

        findViews()
        fillViewsWithData()
    }

    private fun findViews() {
        nameFilmTxtView = findViewById(R.id.txtView_filmName)
        describeFilmTxtView = findViewById(R.id.txtView_descriptionFilm)
        posterImgView = findViewById(R.id.imageView_poster)
    }

    private fun fillViewsWithData() {
        val getIntent = intent
        nameFilmTxtView.text = getIntent.getStringExtra(MainViewModel.NAME_FILM)
        describeFilmTxtView.text = getIntent.getStringExtra(MainViewModel.DESCRIBE_FILM)

        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/original${getIntent.getStringExtra(POSTER_FILM)}")
                .resize(180, 280)
                .centerCrop()
                .into(posterImgView)
    }

}