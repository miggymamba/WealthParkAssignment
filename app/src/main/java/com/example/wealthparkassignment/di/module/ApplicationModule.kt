package com.example.wealthparkassignment.di.module

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.gson.Gson
import com.example.wealthparkassignment.di.util.LongTypeAdapter
import com.example.wealthparkassignment.BuildConfig
import com.example.wealthparkassignment.R
import com.example.wealthparkassignment.data.rest.ApiService
import com.example.wealthparkassignment.data.room.AppDatabase
import com.example.wealthparkassignment.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ActivityViewModelModule::class, FragmentViewModelModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRetrofit(context: Context, preferences: SharedPreferences): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        // set your desired log level
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        retrofitBuilder.client(okHttpClient.build())


        return retrofitBuilder.build()
    }
    /*@Provides
    fun provideGson(): Gson = GsonBuilder().create()*/

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context,sharedPreferences: SharedPreferences): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        // set your desired log level
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    }

//
//    @Singleton
//    @Provides
//    fun provideOkHttpClient(context: Context): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        // set your desired log level
//        interceptor.level =
//            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//            else HttpLoggingInterceptor.Level.NONE
//
//        return OkHttpClient.Builder()
//            .readTimeout(30, TimeUnit.SECONDS)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor(ConnectivityInterceptor(context))
//            .addInterceptor(interceptor)
//            .addInterceptor {
//                val request = it.request()
//                val response = it.proceed(request)
//                if (response.code() == 401) {
//                    Timber.d("SESSION EXPIRED!")
//                }
//                response
//            }
//            .build()
//    }


    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideResources(context: Context) : Resources {
        return context.resources
    }

    @Singleton
    @Provides
    fun provideCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.centerRadius = context.resources.getDimension(com.intuit.sdp.R.dimen._16sdp)
        circularProgressDrawable.strokeWidth = context.resources.getDimension(com.intuit.sdp.R.dimen._2sdp)
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        return circularProgressDrawable
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, Constants.ROOM_DB_NAME
        ).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().setPrettyPrinting().registerTypeAdapter(Long::class.java, LongTypeAdapter()).create()

}
