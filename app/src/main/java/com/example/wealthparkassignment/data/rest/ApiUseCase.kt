package com.example.wealthparkassignment.data.rest

import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ApiUseCase @Inject constructor(private val apiService: ApiService) {

    fun getCities() : Observable<GetCitiesResponse>{
        return apiService.getCities()
    }

    fun getFoods() : Observable<GetFoodsResponse>{
        return apiService.getFoods()
    }
}