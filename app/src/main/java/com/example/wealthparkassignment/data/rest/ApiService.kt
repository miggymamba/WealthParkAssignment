package com.example.wealthparkassignment.data.rest

import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("e81570e822b273f0a366")
    fun getCities() : Observable<GetCitiesResponse>

    @GET("b4dd0d44343f7eb08f9c")
    fun getFoods() : Observable<GetFoodsResponse>
}