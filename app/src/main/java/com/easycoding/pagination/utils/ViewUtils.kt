package com.easycoding.pagination.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadUrl(
    imageUrl: String
) {
    val requestOptions = RequestOptions()
        .fitCenter()
        .format(DecodeFormat.PREFER_ARGB_8888)
        .override(500, 500)

    val requestBuilder = Glide.with(context)
        .load(imageUrl)
        .dontAnimate()
        .apply(requestOptions)

    requestBuilder.into(this)
}