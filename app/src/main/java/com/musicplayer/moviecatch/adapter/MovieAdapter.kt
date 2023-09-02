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
import com.musicplayer.moviecatch.di.dao.GenreDB.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.OnItemClickListener
import java.util.Locale

class MovieAdapter(private val isFirstScreen: Boolean = true, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MovieAdapter.MyCustomHolder>() {
    private var liveData: List<Result>? = null
    private var genreList: List<GenreData>? = null

    fun setLists(liveData: List<Result>, genreList: List<GenreData>) {
        this.liveData = liveData
        this.genreList = genreList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        return MyCustomHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!![position], genreList!!)

        holder.movieItemRelative.setOnClickListener {
            listener.onItemClickListener(liveData!![position], holder.genres)
        }
    }

    override fun getItemCount(): Int {
        if (liveData == null) {
            return 0
        } else if (isFirstScreen) {
            return 4
        }
        return liveData!!.size
    }

    class MyCustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            if (data.genre_ids != null){
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
            }
            txtGenre.text = genres

            if (data.poster_path != null) {
                Glide.with(posterView)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterView)
            }
        }
    }
}