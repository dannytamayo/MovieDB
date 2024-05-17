package com.example.proyectoawm.Interface

import com.example.proyectoawm.Models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Servicios {

    @GET("discover/movie")
    fun getAllMovies(@Query("page") page: Int, @Query("api_key") api_key: String, @Query("sort_by") sort_by: String, @Query("include_adult") include_adult: Boolean, @Query("language") language:String = "es"): Call<ResponseListMovies>

    @GET("search/movie")
    fun getSearchMovies(@Query("query") query: String, @Query("api_key") api_key: String = "f265dea01c64eac51c693deb24d612e4", @Query("include_adult") include_adult: Boolean = false, @Query("language") language:String = "es", @Query("resultLimit") resultLimit: Int = 20): Call<ResponseListMovies>

    @FormUrlEncoded
    @POST("APIMovie/login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("APIMovie/add.php")
    fun addMovie(@Field("title") email: String,
                 @Field("overview") overview: String,
                 @Field("date") date: String,
                 @Field("image") image: String,
                 @Field("score") score: String,
                 @Field("userId") userId: Int
    ): Call<ResponseAddMovie>

    @GET("APIMovie/moviesUser.php")
    fun getMoviesForUser(@Query("userId") userId: Int): Call<ResponseMoviesUser>
}