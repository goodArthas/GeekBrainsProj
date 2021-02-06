package ru.geekbrainsproj.model

data class MovieData(val poster: String, val rating: String, val name: String, val releaseDate: String) {


    companion object {

        fun getDefaultMovieData(): MovieData = MovieData("null", "75%", "SampleText", "Завтра")

    }

}
