package com.example.proyectoawm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoawm.Interface.Servicios
import com.example.proyectoawm.Models.Movie
import com.example.proyectoawm.Models.ResponseListMovies
import com.example.proyectoawm.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(R.layout.fragment_home) {


    val api_key = "f265dea01c64eac51c693deb24d612e4"
    val sort_by = "popularity.desc"
    val include_adult = false
    var isSearching = false

    lateinit var nsvVista: NestedScrollView
    lateinit var rvUsuarios: RecyclerView
    lateinit var pbCargando: ProgressBar
    private lateinit var binding: FragmentHomeBinding

    var contador = 0
    var aux = 1
    var listMovies: ArrayList<Movie> = ArrayList()

    var searchValue = ""

    private val listener = object: AdapterMovies.OnClickListener {
        override fun onMovieClick(movie: Movie) {
            val intent = Intent(requireContext(), ShowMovie::class.java)
            intent.putExtra("title", movie.title)
            intent.putExtra("overview", movie.overview)
            intent.putExtra("image", movie.backdrop_path)
            intent.putExtra("date", movie.release_date)
            // Agrega más información de la película aquí si es necesario
            startActivity(intent)
//            Toast.makeText(requireContext(),movie.title, Toast.LENGTH_LONG ).show()
        }
    }

    //Acceder al adaptador
    private val adapter:AdapterMovies by lazy {
        AdapterMovies(emptyList(), listener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        nsvVista = binding.nsvVista
        rvUsuarios = binding.rvUsuarios
        rvUsuarios.layoutManager = GridLayoutManager(requireContext(), 1)
        pbCargando = binding.pbCargando

        getMovies(requireContext())

        nsvVista.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                contador++
                pbCargando.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    if (contador < 20) {
                        getMovies(requireContext())
                    }
                }, 1000)
            }
        })

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchValue = s.toString()
                if (!searchValue.isEmpty()){
                    isSearching = true
                    listMovies.clear()
                    searchMovies()
                }else {
                    isSearching = false
                    listMovies.clear()
                    aux = 1 // reset the page counter
                    getMovies(requireContext())
                }
            }
        })





        return binding.root

    }

    fun getMovies(context: Context) {

        if (!isSearching) {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(Servicios::class.java)
            val call = service.getAllMovies(aux,api_key,sort_by,include_adult )
            call.enqueue(object : Callback<ResponseListMovies> {
                override fun onResponse(call: Call<ResponseListMovies>, response: Response<ResponseListMovies>) {
                    val response = response.body()

                    response?.results?.forEach{
                        listMovies.add(it)
                    }
                    aux++

                    binding.rvUsuarios.adapter = AdapterMovies(listMovies, listener)

                }

                override fun onFailure(call: Call<ResponseListMovies>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error " + t, Toast.LENGTH_LONG ).show()
                }
            })


        }

    }

    fun searchMovies() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Servicios::class.java)
        val call = service.getSearchMovies(searchValue)
        call.enqueue(object : Callback<ResponseListMovies> {
            override fun onResponse(call: Call<ResponseListMovies>, response: Response<ResponseListMovies>) {
                val response = response.body()
                response?.results?.forEach{
                    listMovies.add(it)
                }
                aux = 1

                binding.rvUsuarios.adapter = AdapterMovies(listMovies, listener)

//                adapter.updateDataList(listMovies)

            }

            override fun onFailure(call: Call<ResponseListMovies>, t: Throwable) {
                Toast.makeText(requireContext(),"Error " + t, Toast.LENGTH_LONG ).show()

            }
        })
    }

}


