package com.example.myapplication.config

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Failure(val exception: Exception) : NetworkResult<Nothing>()
}

inline fun <T> NetworkResult<T>.onSuccess(action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) {
        action(data)
    }
    return this
}

inline fun <T> NetworkResult<T>.onFailure(action: (Exception) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Failure) {
        action(exception)
    }
    return this
}