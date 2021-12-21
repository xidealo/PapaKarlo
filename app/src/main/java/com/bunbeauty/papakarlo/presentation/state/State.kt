package com.bunbeauty.papakarlo.presentation.state

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    class Empty<T> : State<T>()
}
