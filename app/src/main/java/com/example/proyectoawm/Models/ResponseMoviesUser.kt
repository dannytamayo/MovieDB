package com.example.proyectoawm.Models

data class ResponseMoviesUser (
    val movies: List<MovieUser>
)

data class MovieUser(
    val title: String,
    val date: String,
    val overview: String,
    val image: String,
    val score: String,
    val userId: String
)