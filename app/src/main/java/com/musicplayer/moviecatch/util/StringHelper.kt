package com.musicplayer.moviecatch.util

class StringHelper {

    fun getTrName(name: String): String {
        val tr_name: String
        when (name) {
            "Action" -> tr_name = "Aksiyon"
            "Adventure" -> tr_name = "Macera"
            "Animation" -> tr_name = "Animasyon"
            "Comedy" -> tr_name = "Komedi"
            "Crime" -> tr_name = "Suç"
            "Documentary" -> tr_name = "Belgesel"
            "Dram" -> tr_name = "Drama"
            "Family" -> tr_name = "aile"
            "Fantasy" -> tr_name = "Fantazi"
            "History" -> tr_name = "Hikaye"
            "Horror" -> tr_name = "Korku"
            "Music" -> tr_name = "Müzik"
            "Mystery" -> tr_name = "Gizem"
            "Romance" -> tr_name = "Romantik"
            "War" -> tr_name = "Savaş"
            "Western" -> tr_name = "Batı"
            else -> tr_name = "unnamed"
        }
        return tr_name
    }
}