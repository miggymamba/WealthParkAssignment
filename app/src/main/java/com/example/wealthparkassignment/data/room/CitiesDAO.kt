package com.example.wealthparkassignment.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import io.reactivex.rxjava3.core.Completable

@Dao
interface CitiesDAO {
    @Query("DELETE FROM cities_table")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city : GetCitiesResponse.GetCitiesResponseItem) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(order: List<GetCitiesResponse.GetCitiesResponseItem?>?) : Completable

    @Query("SELECT * FROM cities_table ORDER BY city_name ASC")
    fun getAlphabeticalCities(): LiveData<List<GetCitiesResponse.GetCitiesResponseItem>>

    @Query("SELECT * FROM cities_table WHERE city_name=:cityName COLLATE NOCASE")
    fun getCityItem(cityName : String) : GetCitiesResponse.GetCitiesResponseItem?
}