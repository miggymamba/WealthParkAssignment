package com.example.wealthparkassignment.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class GetCitiesResponse : ArrayList<GetCitiesResponse.GetCitiesResponseItem>(){
    @Entity(tableName = "cities_table")
    data class GetCitiesResponseItem(
        @SerializedName("description")
        @ColumnInfo(name = "city_description")
        var description: String = "", // An urban area with a lively character and charm Points of Interest: Dotonbori (nightlife district), Osaka Castle, Osaka Aquarium Kaiyukan, Universal Studios Japan, Kuchu Teien Observatory, Sumiyoshi Taisha Shrine Famous Foods: takoyaki, okonomiyaki, yakiniku, kitsune (deep fried tofu) udon, taiko manju (a baked sweet filled with azuki bean paste that is shaped like a taiko drum)
        @SerializedName("image")
        @ColumnInfo(name = "city_image")
        var image: String = "", // https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/01/a0001601/img/en/a0001601_parts_5b768119c49ca.jpg
        @SerializedName("name")
        @ColumnInfo(name = "city_name")
        @PrimaryKey
        var name: String = "" // Osaka
    )
}