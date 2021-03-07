package ru.geekbrainsproj.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import ru.geekbrainsproj.databinding.DetailMovieActivityBinding
import ru.geekbrainsproj.view_model.MainViewModel
import ru.geekbrainsproj.view_model.MainViewModel.Companion.POSTER_FILM

private const val MAP_REQUEST_CODE = 667

class DetailMovieActivity : AppCompatActivity() {

    lateinit var binding: DetailMovieActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailMovieActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillViewsWithData()

        binding.btnShowArtistCountry?.setOnClickListener { view ->
            if (requestPermissionToReadMapPosition()) {
                startMapActivity()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MAP_REQUEST_CODE)
            }
        }

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

    private fun requestPermissionToReadMapPosition(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun startMapActivity() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions.isNotEmpty()) {
            when (requestCode) {
                MAP_REQUEST_CODE -> {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startMapActivity()
                    } else {
                        showDialogRequirePermission()
                    }
                }

            }

        }

    }

    private fun showDialogRequirePermission() {
        AlertDialog.Builder(this)
                .setTitle("Разрешения")
                .setMessage("Без этого разрешения приложение не сможет предоставить доступ к функции")
                .setPositiveButton("Ok разрешаю") { dialog, which ->
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MAP_REQUEST_CODE)
                    dialog.dismiss()
                }
                .setNegativeButton("Нет, не дам") { dialog, which ->
                    dialog.dismiss()
                }.create().show()
    }

}