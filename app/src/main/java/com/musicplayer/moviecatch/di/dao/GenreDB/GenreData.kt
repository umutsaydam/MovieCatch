package com.musicplayer.moviecatch.di.dao.GenreDB


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val genre_id: Int = 0,
    val en_name: String = "",
    val tr_name: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(this.id)
        p0.writeInt(this.genre_id)
        p0.writeString(this.en_name)
        p0.writeString(this.tr_name)
    }

    companion object CREATOR : Parcelable.Creator<GenreData> {
        override fun createFromParcel(parcel: Parcel): GenreData {
            return GenreData(parcel)
        }

        override fun newArray(size: Int): Array<GenreData?> {
            return arrayOfNulls(size)
        }
    }
}
