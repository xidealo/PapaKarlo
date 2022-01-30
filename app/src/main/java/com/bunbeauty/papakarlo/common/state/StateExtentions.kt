package com.bunbeauty.papakarlo.common.state

fun <T : Any> T.toStateSuccess(): State.Success<T> {
    return State.Success(this)
}

fun <T : Any> T?.toSuccessOrEmpty(): State<T> {
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

fun <T : Any> T?.toStateNullableSuccess(): State.Success<T?> {
    return State.Success(this)
}