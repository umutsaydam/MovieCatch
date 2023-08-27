package com.musicplayer.moviecatch.adapter

import android.icu.text.ListFormatter.Width
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
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
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val videoWebView: WebView = itemView.findViewById(R.id.videoWebView)
        var customView: FrameLayout = itemView.findViewById(R.id.customView)

        fun loadVideo(url: String) {
           /* videoWebView.loadData(resizeVideo(url), "text/html", "utf-8")
            videoWebView.settings.javaScriptEnabled = true
            videoWebView.webChromeClient = WebChromeClient()*/
            videoWebView.apply {
                settings.javaScriptEnabled = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                loadData(resizeVideo(url), "text/html", "utf-8")
                webChromeClient = object: WebChromeClient(){
                    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                        super.onShowCustomView(view, callback)
                        videoWebView.visibility = View.GONE
                        customView.visibility = View.VISIBLE
                        customView.addView(view)
                        cardView.minimumWidth = 500
                        cardView.minimumHeight = 500
                    }

                    override fun onHideCustomView() {
                        super.onHideCustomView()
                        videoWebView.visibility = View.VISIBLE
                        customView.visibility = View.GONE
                    }
                }
            }
        }

        private fun resizeVideo(url: String): String{
           val newUrl = url.substring(0,15)+"280"+url.substring(18,28)+"160"+url.substring(31, url.length)
            Log.d("R/A", newUrl + " *1*0*")
            return newUrl
        }
    }
}