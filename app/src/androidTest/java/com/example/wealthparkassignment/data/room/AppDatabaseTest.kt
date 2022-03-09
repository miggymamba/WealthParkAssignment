package com.example.wealthparkassignment.data.room

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.util.extensions.blockingObserve
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors


@RunWith(AndroidJUnit4::class)

class AppDatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var citiesDAO: CitiesDAO
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).setTransactionExecutor(Executors.newSingleThreadExecutor()).allowMainThreadQueries()
            .build()
        citiesDAO = appDatabase.citiesDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }


    @Test
    fun writeAndReadCity() {
        val city = GetCitiesResponse.GetCitiesResponseItem(
            description = "Hokkaido (北海道, Hokkaidō) is the second largest, northernmost and least " +
                    "developed of Japan's four main islands. Its weather is harsh in winter with lots of snowfall, below zero temperatures and frozen " +
                    "seas, while in summer it does not get as hot and humid as in the other parts of the country.",
            image = "https://hokkaido-treasure.com/wp-content/uploads/2020/09/biei-pc.jpg",
            name = "Hokkaido"
        )

        citiesDAO.insert(city).test()

        val cities = citiesDAO.getAlphabeticalCities().blockingObserve()
        assertThat(cities!!.contains(city)).isTrue()
    }

    @Test
    fun readFakeCity() {
        val city = citiesDAO.getCityItem("mimishihoro")
        assertThat(city == null).isTrue()
    }

    @Test
    fun readRealCity() {
        val city = GetCitiesResponse.GetCitiesResponseItem(
            description = "Hokkaido (北海道, Hokkaidō) is the second largest, northernmost and least " +
                    "developed of Japan's four main islands. Its weather is harsh in winter with lots of snowfall, below zero temperatures and frozen " +
                    "seas, while in summer it does not get as hot and humid as in the other parts of the country.",
            image = "https://hokkaido-treasure.com/wp-content/uploads/2020/09/biei-pc.jpg",
            name = "Hokkaido"
        )

        citiesDAO.insert(city).test()

        val item = citiesDAO.getCityItem("hokkaido")

        assertThat(item != null).isTrue()
    }


}