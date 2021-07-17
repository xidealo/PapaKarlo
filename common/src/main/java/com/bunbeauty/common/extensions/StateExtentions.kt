package com.bunbeauty.common.extensions

import com.bunbeauty.common.State

fun <T : Any> T.toStateSuccess(): State.Success<T> {
    return State.Success(this)
}

fun <T : Any> T?.toStateNullableSuccess(): State.Success<T?> {
    return State.Success(this)
}
