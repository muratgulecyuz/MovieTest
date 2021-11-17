package com.applogist.movietest.utils

import android.widget.ImageView
import com.applogist.movietest.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this.context)
        .load("$BASE_URL_FOR_IMAGE$imageUrl").centerCrop()
        .error(R.drawable.ic_launcher_background)
        .into(this)
}