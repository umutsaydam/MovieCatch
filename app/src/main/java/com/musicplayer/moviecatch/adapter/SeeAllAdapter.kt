package com.musicplayer.moviecatch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.di.dao.GenreDB.GenreData
import com.musicplayer.moviecatch.models.Result
import com.musicplayer.moviecatch.util.OnItemClickListener
import java.util.Locale
import javax.inject.Inject

class SeeAllAdapter @Inject constructor(private val listener: OnItemClickListener) :
    PagingDataAdapter<Result, SeeAllAdapter.SeeAllViewHolder>(differCallback) {
    private var genreList: List<GenreData>? = null

    fun setLists(genreList: List<GenreData>) {
        this.genreList = genreList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllViewHolder {
        return SeeAllViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.see_all_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SeeAllViewHolder, position: Int) {
        Log.d("R8/W", "adapter is working")
        holder.bind(getItem(position)!!, genreList!!)

        holder.movieItemRelative.setOnClickListener {
            listener.onItemClickListener(getItem(position)!!, holder.genres)
        }
        holder.setIsRecyclable(false)
        Log.d("R8/W", "end of the onBindViewHolder")
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


            if (data.genre_ids?.isNotEmpty()!!) {
                for (id in data.genre_ids!!) {
                    val result = genreList.find { x -> x.genre_id == id }

                    if (result != null) {
                        genres += if (lang == "tr") {
                            "${result.tr_name}, "
                        } else {
                            "${result.en_name}, "
                        }
                    }
                    Log.d("R/E", result!!.en_name)
                }

                if (genres != "") {
                    genres = genres.substring(0, genres.lastIndex - 1)
                }
            }

            txtGenre.text = genres

            if (data.poster_path != null) {
                Glide.with(posterView)
                    .load("https://image.tmdb.org/t/p/w342/" + data.poster_path)
                    .into(posterView)
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                Log.d("R8/Q", oldItem.id.toString() + " 89 " + newItem.id.toString())
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                Log.d("R8/W", oldItem.toString() + " 94 " + newItem.toString())
                return oldItem == newItem
            }
        }
    }
}