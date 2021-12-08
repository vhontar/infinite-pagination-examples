package com.easycoding.pagination.datasource.network.utils

import android.content.Context
import com.easycoding.pagination.business.constants.AppConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    context: Context?,
    dispatcher: CoroutineDispatcher,
    networkCall: suspend () -> T?
): NetworkResult<T?> {
    return withContext(dispatcher) {
        if (NetworkUtils.checkNetwork(context)) {
            NetworkResult.InternetConnectionError
        }
        try {
            // throws TimeoutCancellationException
            withTimeout(AppConstants.NETWORK_TIMEOUT) {
                NetworkResult.Success(networkCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is SocketTimeoutException -> {
                    NetworkResult.ServerDownError
                }
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    NetworkResult.GenericError(code, NetworkErrors.NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    NetworkResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    NetworkResult.GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    NetworkResult.GenericError(
                        null,
                        NetworkErrors.NETWORK_ERROR_UNKNOWN
                    )
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        NetworkErrors.ERROR_UNKNOWN
    }
}