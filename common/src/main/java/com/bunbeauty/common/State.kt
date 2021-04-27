package com.bunbeauty.common

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    class Empty<T> : State<T>()
    data class Error<T>(val apiError: ApiError) : State<T>()
}
