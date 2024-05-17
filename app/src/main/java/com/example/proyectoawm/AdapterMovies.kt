package com.example.proyectoawm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectoawm.Models.Movie
import com.example.proyectoawm.databinding.FragmentHomeBinding
import com.example.proyectoawm.databinding.ItemRvPeliculasBinding

class AdapterMovies (var listMovies: List<Movie> = emptyList(),  val listener: OnClickListener):RecyclerView.Adapter<AdapterMovies.ViewHolder>(){

    interface OnClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvPeliculasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    class ViewHolder(private val binding: ItemRvPeliculasBinding,val listener: OnClickListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.title.text = movie.title

            if(!movie.overview.isNullOrBlank() && movie.overview.length > 100) {
                binding.overview.text = movie.overview.slice(0 until 100) + "..."
            } else {
                binding.overview.text = ""
            }

            binding.releaseDate.text = movie.release_date
            Glide.with(binding.image.context)
                .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + movie.backdrop_path)
                .into(binding.image)

            itemView.setOnClickListener {
                listener?.onMovieClick(movie)
            }

        }
    }


    fun updateDataList(movies: List<Movie>) {
        this.listMovies = movies
        notifyDataSetChanged()
    }
}
