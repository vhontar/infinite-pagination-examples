package com.easycoding.pagination.datasource.network.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    @JvmStatic
    fun checkNetwork(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        val connection =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connection.activeNetworkInfo
        return netInfo == null
    }
}