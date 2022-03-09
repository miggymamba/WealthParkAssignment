package com.example.wealthparkassignment.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.wealthparkassignment.base.BaseViewModel
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse
import com.example.wealthparkassignment.data.rest.ApiUseCase
import com.example.wealthparkassignment.data.room.AppDatabase
import com.example.wealthparkassignment.util.PairMediatorLiveData
import com.example.wealthparkassignment.util.single_live_event.LiveEvent
import com.example.wealthparkassignment.util.single_live_event.SingleLiveEvent
import io.reactivex.rxjava3.core.Observable
//import io.reactivex.rxjava3.core.Flowable.just
//import io.reactivex.rxjava3.core.Maybe.just
//import io.reactivex.rxjava3.core.Single.just
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(val apiUseCase: ApiUseCase, private val appDatabase: AppDatabase) : BaseViewModel() {

    val statusGetCities = SingleLiveEvent<LiveEvent>()
    val statusGetFoods = SingleLiveEvent<LiveEvent>()

    val _cities = MutableLiveData<GetCitiesResponse>()

    val _foods = MutableLiveData<GetFoodsResponse>()

    val citiesLiveData = appDatabase.citiesDAO().getAlphabeticalCities()
    val foodsLiveData = appDatabase.foodsDAO().getAlphabeticalFoods()
    var pairMediatorLiveData = PairMediatorLiveData(citiesLiveData, foodsLiveData)


    fun refreshData(){
        //appDatabase.citiesDAO().insert(GetCitiesResponse.GetCitiesResponseItem(description = "test", image = "test", name = "test"))
        pairMediatorLiveData.value = null
        pairMediatorLiveData = PairMediatorLiveData(citiesLiveData, foodsLiveData)
        getCities()
        getFoods()
    }

    fun getCities() {
        addDisposable(apiUseCase.getCities()
            .doOnSubscribe { statusGetCities.postValue(LiveEvent.Loading()) }
            .compose(applySchedulers())
            .subscribe({
                statusGetCities.postValue(LiveEvent.Success())
                _cities.value = it
                Observable.fromCallable { Runnable { appDatabase.citiesDAO().insertAllCities(it.toMutableList()) } .run()}.subscribeOn(Schedulers.io())
                    .subscribe()
            }, {
                statusGetCities.postValue(LiveEvent.Error())
            })
        )
    }

    fun getFoods() {
        addDisposable(apiUseCase.getFoods()
            .doOnSubscribe { statusGetFoods.postValue(LiveEvent.Loading()) }
            .compose(applySchedulers())
            .subscribe({
                statusGetFoods.postValue(LiveEvent.Success())
                _foods.value = it
                Observable.fromCallable { Runnable { appDatabase.foodsDAO().insertAllFoods(it.toMutableList()) } .run()}.subscribeOn(Schedulers.io())
                    .subscribe()
            }, {
                statusGetFoods.postValue(LiveEvent.Error())
            })
        )

    }
}