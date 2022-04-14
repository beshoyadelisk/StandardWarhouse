package com.gargour.warehouse.util

import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gargour.warehouse.R

object ViewExt {
    private var toast: Toast? = null
    fun Fragment.showToast(message: String) {
        toast?.cancel()
        toast = makeText(this.requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun View.toVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.toGone() {
        this.visibility = View.GONE
    }

    fun View.visibility(status: Int) {
        this.visibility = status
    }

    fun Button.setSelected() {
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorMain))
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    fun Button.setNotSelected() {
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        setTextColor(ContextCompat.getColor(context, R.color.black))
    }

}
