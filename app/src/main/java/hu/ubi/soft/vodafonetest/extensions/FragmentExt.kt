package hu.ubi.soft.vodafonetest.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showError(message: String, length: Int = Snackbar.LENGTH_LONG) =
    this.let { view?.showSnackBar(message, length) }