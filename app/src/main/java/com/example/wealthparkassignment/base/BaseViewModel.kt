package com.example.wealthparkassignment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.HttpException

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


abstract class BaseViewModel : ViewModel() {
     val _apiLoading = MutableLiveData<Boolean>()
     val _apiError = MutableLiveData<String>()
     val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage
    val apiError: LiveData<String> get() = _apiError
    val apiLoading: LiveData<Boolean> get() = _apiLoading
    val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun getRawApiErrorMessage(t: Throwable): String {
        var errorMessage = ""
        if (t is HttpException) {
            val responseBody = t.response()!!.errorBody()!!.string()
            try {
                val jObjError = JSONObject(responseBody)
                errorMessage = jObjError.get("Message").toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return errorMessage
    }

    fun addDisposable(d: Disposable) {
        disposable.add(d)
    }


}