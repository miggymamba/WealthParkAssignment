package com.example.wealthparkassignment.base

//import com.facebook.stetho.Stetho
import com.example.wealthparkassignment.BuildConfig
import com.example.wealthparkassignment.di.component.ApplicationComponent
import com.example.wealthparkassignment.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {

        val component: ApplicationComponent =
            DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())

        return component
    }

}