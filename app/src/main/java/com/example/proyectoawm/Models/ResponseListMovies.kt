package com.example.proyectoawm.Models

data class ResponseListMovies (
    val results: List<Movie>
)

data class Movie(
    val title: String,
    val release_date: String,
    val overview: String,
    val backdrop_path: String
)