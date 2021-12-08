package com.easycoding.pagination.utils

import android.util.Log
import com.easycoding.pagination.business.constants.AppConstants

object LoggerUtils {
    fun logException(e: Throwable) {
        Log.e(AppConstants.APP_TAG, e.message ?: "")
    }
}