package com.example.wealthparkassignment.di.component

import android.app.Application
import com.example.wealthparkassignment.di.module.ActivityBindingModule
import com.example.wealthparkassignment.di.module.ApplicationModule
import com.example.wealthparkassignment.di.module.ContextModule
import com.example.wealthparkassignment.di.module.NetModule
import com.example.wealthparkassignment.base.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class, NetModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication?> {
    fun inject(application: BaseApplication)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}