package hu.ubi.soft.vodafonetest.extensions

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import hu.ubi.soft.vodafonetest.R

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showIf(condition: Boolean) {
    if (condition) show() else hide()
}

fun View.hideIf(condition: Boolean) {
    if (condition) hide() else show()
}

fun View.showSnackBar(snackBarText: String, timeLength: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, snackBarText, timeLength)
    snack.view.setBackgroundResource(R.drawable.snackbar_error_background)
    val textView = snack.view.findViewById<TextView>(R.id.snackbar_text)
    textView.maxLines = 5
    snack.show()
}