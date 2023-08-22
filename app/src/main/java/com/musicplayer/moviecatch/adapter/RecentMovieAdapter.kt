package com.musicplayer.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.models.Result

class RecentMovieAdapter(private val isFirstScreen: Boolean = true) :
    RecyclerView.Adapter<RecentMovieAdapter.MyCustomHolder>() {
    private var liveData: List<Result>? = null

    fun setList(liveData: List<Result>) {
        this.liveData = liveData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        return MyCustomHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recent_movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!![position])
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
        private val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        private val txtGenre = itemView.findViewById<TextView>(R.id.txtGenre)
        private val posterView = itemView.findViewById<ImageView>(R.id.posterView)
        private val txtReleaseDate = itemView.findViewById<TextView>(R.id.txtReleaseDate)
        private val txtVoteAverage = itemView.findViewById<TextView>(R.id.txtVoteAverage)

        fun bind(data: Result) {
            textTitle.text = data.title
            txtReleaseDate.text = data.release_date
            txtVoteAverage.text = data.vote_average.toString() + " / 10"
            Glide.with(posterView)
                .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                .into(posterView)
        }
    }
}