package com.example.wealthparkassignment.util

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProvider @Inject constructor(val context : Context) {

    fun provideString(@StringRes stringId : Int) : String{
        return context.getString(stringId)
    }
}