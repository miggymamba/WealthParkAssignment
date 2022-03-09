package com.example.wealthparkassignment.util.extensions

import android.animation.LayoutTransition
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun ViewGroup.enableLayoutTransition() {
    this.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
}

fun snack(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("Close"){}.setAnimationMode(
        BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
}