package com.example.wealthparkassignment.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class GetFoodsResponse : ArrayList<GetFoodsResponse.GetFoodsResponseItem>(){
    @Entity(tableName = "foods_table")
    data class GetFoodsResponseItem(
        @SerializedName("image")
        @ColumnInfo(name = "food_image")
        var image: String = "", // https://onoen.jp/wp-content/uploads/2020/04/img_71.jpg
        @SerializedName("name")
        @ColumnInfo(name = "food_name")
        @PrimaryKey
        var name: String = "" // Motcha
    )
}