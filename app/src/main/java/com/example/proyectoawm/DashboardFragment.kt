package com.example.proyectoawm

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyectoawm.Interface.Servicios
import com.example.proyectoawm.Models.Movie
import com.example.proyectoawm.Models.MovieUser
import com.example.proyectoawm.Models.ResponseMoviesUser
import com.example.proyectoawm.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {


    private lateinit var binding: FragmentDashboardBinding
    var listMovies: ArrayList<MovieUser> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val context = requireActivity()
        val user = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userId = user.getInt("userId", -1)

        cargarPeliculas(userId)

        return binding.root
    }

    private fun cargarPeliculas(userId: Int) {
        listMovies = ArrayList()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.79.7.111/APIMovie/moviesUser.php/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Servicios::class.java)
        val call = service.getMoviesForUser(userId)
        call.enqueue(object : Callback<ResponseMoviesUser>{
            override fun onResponse(call: Call<ResponseMoviesUser>, response: Response<ResponseMoviesUser>) {
                val response = response.body()

                response?.movies?.forEach{
                    listMovies.add(it)
                }


                binding.lvMovies.adapter = CustomAdapter(requireContext(),listMovies)

            }

            override fun onFailure(call: Call<ResponseMoviesUser>, t: Throwable) {
                Toast.makeText(requireContext(), "Error " + t, Toast.LENGTH_LONG ).show()
            }


        })

    }

}