package com.example.wealthparkassignment.ui.details

import androidx.lifecycle.MutableLiveData
import com.example.wealthparkassignment.base.BaseViewModel
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse
import com.example.wealthparkassignment.data.room.AppDatabase
import com.example.wealthparkassignment.util.Constants
import com.example.wealthparkassignment.util.single_live_event.LiveEvent
import com.example.wealthparkassignment.util.single_live_event.SingleLiveEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val appDatabase: AppDatabase) : BaseViewModel() {

    val itemName = MutableLiveData<String>()
    val itemImage = MutableLiveData<String>()
    val itemDescription = MutableLiveData<String>()

    val statusQuery = SingleLiveEvent<LiveEvent>()

    init {
        itemName.value = ""
        itemImage.value = ""
        itemDescription.value = ""
    }

    fun initQuery(type: String, name: String) {
        if (type == Constants.INTENT_TYPE_CITY) {
            var cityItem: GetCitiesResponse.GetCitiesResponseItem? = null
            Observable.fromCallable {
                Runnable {
                    cityItem = appDatabase.citiesDAO().getCityItem(name)
                    if (cityItem != null) {
                        itemName.postValue(cityItem!!.name)
                        itemImage.postValue(cityItem!!.image)
                        itemDescription.postValue(cityItem!!.description)
                    } else {
                        statusQuery.postValue(LiveEvent.Error("error querying data"))
                    }

                }.run()
            }.subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.d("initQuery success $cityItem")
                }, {
                    Timber.e("error querying data")
                    statusQuery.postValue(LiveEvent.Error("error querying data"))
                })

        } else {
            var foodItem: GetFoodsResponse.GetFoodsResponseItem? = null

            Observable.fromCallable {
                Runnable {
                    if (foodItem != null) {
                        foodItem = appDatabase.foodsDAO().getFoodItem(name)
                        itemName.postValue(foodItem!!.name)
                        itemImage.postValue(foodItem!!.image)
                    } else {
                        statusQuery.postValue(LiveEvent.Error("error querying data"))
                        Timber.e("error querying data")
                    }

                }.run()
            }.subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.d("initQuery success $foodItem")
                }, {
                    statusQuery.postValue(LiveEvent.Error("error querying data"))
                    Timber.d("initQuery error ${it.message}")
                })
        }
    }
}