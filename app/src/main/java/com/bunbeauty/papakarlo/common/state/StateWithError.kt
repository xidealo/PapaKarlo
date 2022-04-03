package com.bunbeauty.papakarlo.common.state

sealed class StateWithError<T> {
    class Loading<T> : StateWithError<T>()
    data class Success<T>(val data: T) : StateWithError<T>()
    class Empty<T> : StateWithError<T>()
    data class Error<T>(val message: String) : StateWithError<T>()
}
