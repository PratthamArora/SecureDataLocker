package com.pratthamarora.jetpacksecurity.util

import android.view.Gravity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.jetpacksecurity.R

object Utility {
    fun displaySnackBar(msg: String, view: View) {
        val snackbar =
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.anchorId = R.id.nav_view
        layoutParams.anchorGravity = Gravity.TOP
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }
}