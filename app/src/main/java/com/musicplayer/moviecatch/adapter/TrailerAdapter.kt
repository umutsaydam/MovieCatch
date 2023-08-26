package com.musicplayer.moviecatch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.moviecatch.R
import com.musicplayer.moviecatch.models.YoutubeTrailerModel

class TrailerAdapter : RecyclerView.Adapter<TrailerAdapter.TrailViewHolder>() {
    private var trailerList: ArrayList<YoutubeTrailerModel> = ArrayList()

    fun addItems(item: YoutubeTrailerModel) {
        trailerList.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailViewHolder {
        return TrailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }

    override fun onBindViewHolder(holder: TrailViewHolder, position: Int) {
        holder.loadVideo(trailerList[position].html)
    }

    class TrailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoWebView: WebView = itemView.findViewById(R.id.videoWebView)

        fun loadVideo(url: String) {
            Log.d("R/A", url + " *1*0*")
            videoWebView.loadData(url, "text/html", "utf-8")
            videoWebView.settings.javaScriptEnabled = true
            videoWebView.webChromeClient = WebChromeClient()
        }
    }
}