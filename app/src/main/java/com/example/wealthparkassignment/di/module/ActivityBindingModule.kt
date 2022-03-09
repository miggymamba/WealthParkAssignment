package com.example.wealthparkassignment.di.module

import com.example.wealthparkassignment.ui.details.DetailsActivity
import com.example.wealthparkassignment.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {


    @ContributesAndroidInjector //(modules = [MainFragmentsBindingModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector //(modules = [MainFragmentsBindingModule::class])
    abstract fun bindDetailsActivity(): DetailsActivity

}