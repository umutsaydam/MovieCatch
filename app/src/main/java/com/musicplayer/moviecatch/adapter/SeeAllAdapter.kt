package com.musicplayer.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.di.dao.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.OnItemClickListener
import java.util.Locale

class SeeAllAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SeeAllAdapter.SeeAllViewHolder>() {
    private var movieList: List<Result>? = null
    private var genreList: List<GenreData>? = null

    fun setLists(list: List<Result>, genreList: List<GenreData>) {
        movieList = list
        this.genreList = genreList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllViewHolder {
        return SeeAllViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.see_all_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (movieList == null) {
            return 0
        }
        return movieList!!.size
    }

    override fun onBindViewHolder(holder: SeeAllViewHolder, position: Int) {
        holder.bind(movieList!![position], genreList!!)

        holder.movieItemRelative.setOnClickListener {
            listener.onItemClickListener(movieList!![position], holder.genres)
        }
    }

    class SeeAllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val txtGenre: TextView = itemView.findViewById(R.id.txtGenre)
        private val posterView: ImageView = itemView.findViewById(R.id.posterView)
        val movieItemRelative: RelativeLayout = itemView.findViewById(R.id.movieItemRelative)

        var genres = ""

        fun bind(data: Result, genreList: List<GenreData>) {
            textTitle.text = data.title
            txtGenre.text = ""

            val lang = Locale.getDefault().language
            genres = ""
            for (id in data.genre_ids) {
                val result = genreList.find { x -> x.genre_id == id }

                if (result != null) {
                    genres += if (lang == "tr") {
                        "${result.tr_name}, "
                    } else {
                        "${result.en_name}, "
                    }
                }
            }

            genres = genres.substring(0, genres.lastIndex - 1)
            txtGenre.text = genres

            Glide.with(posterView)
                .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                .into(posterView)
        }
    }
}