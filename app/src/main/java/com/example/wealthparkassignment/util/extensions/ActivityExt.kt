package com.example.wealthparkassignment.util.extensions

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import kotlin.reflect.KClass

fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.commit { replace(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(frameId: Int, fragmentClass: KClass<out Fragment>) {
    supportFragmentManager.commit { replace(frameId, fragmentClass.java, null, null) }
}

internal fun <T : Any, L : LiveData<T>> AppCompatActivity.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

internal fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

internal fun <T : Any, L : LiveData<T>> AppCompatActivity.justObserve(liveData: L) {
    liveData.observe(this, Observer { })
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : AppCompatActivity> Fragment.startActivityFromFragment(block: Intent.() -> Unit = {}) {
    startActivity(Intent(requireContext(), T::class.java).apply(block))
}

inline fun <reified T : AppCompatActivity> Context.startActivityFromAdapter(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(
    requestCode: Int = -1,
    block: Intent.() -> Unit = {}) {
    startActivityForResult(Intent(this, T::class.java).apply(block), requestCode)
}


inline fun AppCompatActivity.launchActivityResult(intent: Intent, crossinline resultOk : () -> Unit, crossinline resultCanceled: () -> Unit){
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when(it.resultCode){
            DaggerAppCompatActivity.RESULT_OK -> resultOk()
            else -> resultCanceled()
        }
    }.launch(intent)
}
