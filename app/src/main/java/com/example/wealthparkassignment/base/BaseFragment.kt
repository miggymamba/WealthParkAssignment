package com.example.wealthparkassignment.base

import android.content.Context
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment


abstract class BaseFragment : DaggerFragment() {
    var baseActivity: DaggerAppCompatActivity? = null
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as DaggerAppCompatActivity
    }

    //    protected abstract fun init()
//    protected abstract fun setAdapters()
//    protected abstract fun setListeners()
//    protected abstract fun setObservers()

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }
}