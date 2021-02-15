package ru.geekbrainsproj.model.pojo

import kotlinx.serialization.Serializable

@Serializable
data class MovieData(
        val page: Int,
        val movieInfo: List<MovieInfo>,
        val total_pages: Int,
        val total_results: Int
)