package com.applogist.movietest.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.applogist.movietest.R
import com.google.android.material.button.MaterialButton

fun Context.showDialog(
    cancelable: Boolean = true,
    cancelableTouchOutside: Boolean = true,
) {
    MaterialDialog(this).show {
        cancelable(cancelable)
        cancelOnTouchOutside(cancelableTouchOutside)
        setContentView(R.layout.view_custom_dialog)
        val neutralButton = this.findViewById<MaterialButton>(R.id.btnNeutral)
        neutralButton.setOnClickListener {
            dismiss()
        }
    }
}