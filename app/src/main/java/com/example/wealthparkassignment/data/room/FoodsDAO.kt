package com.example.wealthparkassignment.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse

@Dao
interface FoodsDAO {
    @Query("DELETE FROM foods_table")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food : GetFoodsResponse.GetFoodsResponseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFoods(order: List<GetFoodsResponse.GetFoodsResponseItem?>?)

    @Query("SELECT * FROM foods_table ORDER BY food_name ASC")
    fun getAlphabeticalFoods(): LiveData<List<GetFoodsResponse.GetFoodsResponseItem>>

    @Query("SELECT * FROM foods_table WHERE food_name=:foodName COLLATE NOCASE")
    fun getFoodItem(foodName : String) : GetFoodsResponse.GetFoodsResponseItem?
}