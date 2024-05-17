package com.example.proyectoawm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.proyectoawm.Models.Movie
import com.example.proyectoawm.Models.MovieUser
import com.example.proyectoawm.databinding.ItemMovieBinding

class CustomAdapter(var context: Context, var movies: List<MovieUser>):BaseAdapter() {



    private class ViewHolder(row: View?){

        lateinit var binding: ItemMovieBinding

        init {
            binding = ItemMovieBinding.bind(row!!)
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.item_movie, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var movie: MovieUser = getItem(position) as MovieUser
        viewHolder.binding.titleM.text = movie.title
        viewHolder.binding.scoreM.text = movie.score
        Glide.with(viewHolder.binding.imageM.context)
            .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + movie.image)
            .apply(RequestOptions().override(300, 400))
            .into(viewHolder.binding.imageM)

        return view as View
    }

    override fun getCount(): Int {
        return movies.count()
    }

    override fun getItem(position: Int): Any {
        return movies.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}