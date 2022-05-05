package com.bunbeauty.papakarlo.extensions

import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.state.StateWithError

fun <T> T.toStateSuccess(): State.Success<T> {
    return State.Success(this)
}

fun <T> T.toStateWithErrorSuccess(): StateWithError.Success<T> {
    return StateWithError.Success(this)
}

fun <T> T?.toStateSuccessOrError(errorMessage: String): StateWithError<T> {
    return this?.toStateWithErrorSuccess() ?: StateWithError.Error(errorMessage)
}

fun <T> T?.toSuccessOrEmpty(): State<T> {
    return if (this == null) {
        State.Empty()
    } else {
        if (this is List<*> && isEmpty()) {
            State.Empty()
        } else {
            State.Success(this)
        }
    }
}

fun <T> T?.toStateNullableSuccess(): State.Success<T?> {
    return State.Success(this)
}

fun <T> T?.toState(errorMessage: String): StateWithError<T> {
    return if (this == null) {
        StateWithError.Error(errorMessage)
    } else {
        if (this is List<*> && isEmpty()) {
            StateWithError.Empty()
        } else {
            StateWithError.Success(this)
        }
    }
}
