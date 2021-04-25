package com.bunbeauty.data

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Data<T>(val data: T) : State<T>()
    class Empty<T> : State<T>()
    data class Error<T>(val error: String) : State<T>()
}
