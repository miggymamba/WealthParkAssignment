package com.example.wealthparkassignment.base



import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


abstract class BaseActivity : DaggerAppCompatActivity() {
    var timerOngoing = false
    private lateinit var viewModel: BaseViewModel

    override fun onResume() {
        Timber.d("Active Activity: ${this::class.java.simpleName}")
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        super.onResume()
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onPause() {
        Timber.d("Left Activity: ${this::class.java.simpleName}")
        super.onPause()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val view = currentFocus
        val ret = super.dispatchTouchEvent(event)

        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            w!!.getLocationOnScreen(scrcoords)
            val x = event.rawX + w.left - scrcoords[0]
            val y = event.rawY + w.top - scrcoords[1]

            if (event.action == MotionEvent.ACTION_UP
                && (x < w.left || x >= w.right || y < w.top || y > w
                    .bottom)
            ) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    window.currentFocus
                    !!.windowToken, 0
                )
            }
        }
        return ret
    }

    fun printExtras() {
        Timber.d("printExtras ${intent.extras?.keySet()?.map { it to intent.extras?.get(it) }}")
    }

}