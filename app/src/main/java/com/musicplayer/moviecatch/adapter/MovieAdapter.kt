package com.musicplayer.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.models.Result

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MyCustomHolder>() {
    var liveData: List<Result>? = null

    fun setList(liveData: List<Result>) {
        this.liveData = liveData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        return MyCustomHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!![position])
    }

    override fun getItemCount(): Int {
        if (liveData == null){
            return 0
        }
        return liveData!!.size
    }

    class MyCustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        fun bind(data: Result){
            title.text = data.title
        }
    }
}