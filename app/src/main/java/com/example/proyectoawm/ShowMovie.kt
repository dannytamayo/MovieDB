package com.example.proyectoawm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.proyectoawm.Interface.Servicios
import com.example.proyectoawm.Models.ResponseAddMovie
import com.example.proyectoawm.databinding.ActivityShowMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowMovie : AppCompatActivity() {

    private lateinit var binding: ActivityShowMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val overview = intent.getStringExtra("overview")
        val image = intent.getStringExtra("image")
        val date = intent.getStringExtra("date")

//        Toast.makeText(this,title, Toast.LENGTH_LONG ).show()

        binding.titleShow.text = title
        binding.overviewShow.text = overview
        binding.dateShow.text = date
        Glide.with(binding.imageShow.context)
            .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + image)
            .into(binding.imageShow)

        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        val userId = user.getInt("userId", -1)

        binding.userId.text = userId.toString()

        binding.btnAgregar.setOnClickListener {

            val score = binding.editCalificacion.text.toString()

            addMovieDash(title, overview, image, date,score, userId)
        }

    }

    private fun addMovieDash(title: String?, overview: String?, image: String?, date: String?,score:String, userId: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.79.7.111/APIMovie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Servicios::class.java)

        val call = service.addMovie(title ?: "", overview ?: "", date ?: "", image ?: "",score, userId)
        call.enqueue(object: Callback<ResponseAddMovie>{
            override fun onResponse(call: Call<ResponseAddMovie>, response: Response<ResponseAddMovie>) {
                val response = response.body()

                if (response != null) {
                    if (response.success){

                        val intent = Intent(this@ShowMovie, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

            override fun onFailure(call: Call<ResponseAddMovie>, t: Throwable) {
                Toast.makeText(this@ShowMovie, "Error " + t, Toast.LENGTH_LONG ).show()
            }

        })

    }


}