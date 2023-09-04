package com.musicplayer.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.di.dao.FavMovieDB.FavMovieData
import com.musicplayer.moviecatch.di.dao.GenreDB.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.OnItemClickListener
import java.util.Locale

class FavMovieAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {
    private var favMovieList: List<FavMovieData> = listOf()
    private var genreList: List<GenreData> = listOf()

    fun setFavMovieList(favMovies: List<FavMovieData>, genres: List<GenreData>) {
        favMovieList = favMovies
        genreList = genres
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        return FavMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fav_movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favMovieList.size
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        val favMovie = favMovieList[position]
        holder.bind(favMovie, genreList)

        holder.recentMovieLinear.setOnClickListener {
            val result = Result(
                favMovie.backdrop_path,
                favMovie.genre_ids,
                favMovie.id,
                favMovie.overview,
                favMovie.popularity,
                favMovie.poster_path,
                favMovie.release_date,
                favMovie.title,
                favMovie.vote_average,
                favMovie.genrestringTr,
                favMovie.genrestring
            )
            listener.onItemClickListener(result, holder.genres)
        }
    }

    class FavMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recentMovieLinear: LinearLayout = itemView.findViewById(R.id.recentMovieLinear)
        private val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        private val txtGenre = itemView.findViewById<TextView>(R.id.txtGenre)
        private val posterView = itemView.findViewById<ImageView>(R.id.posterView)
        private val txtReleaseDate = itemView.findViewById<TextView>(R.id.txtReleaseDate)
        private val txtVoteAverage = itemView.findViewById<TextView>(R.id.txtVoteAverage)
        var genres = ""

        fun bind(data: FavMovieData, genreList: List<GenreData>) {
            textTitle.text = data.title
            txtGenre.text = ""

            val lang = Locale.getDefault().language

            genres = ""
            if (data.genre_ids != null) {
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

                if (genres != ""){
                    genres = genres.substring(0, genres.lastIndex - 1)
                }
            }
            txtGenre.text = genres

            txtReleaseDate.text = data.release_date
            txtVoteAverage.text = "${data.vote_average} / 10"

            if (data.poster_path != null) {
                Glide.with(posterView)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterView)
            }
        }
    }
}