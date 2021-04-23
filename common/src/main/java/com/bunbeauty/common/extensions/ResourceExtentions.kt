package com.bunbeauty.common.extensions

import com.bunbeauty.common.Resource

fun <T : Any> T.toResourceSuccess(): Resource.Success<T> {
    return Resource.Success(this)
}

fun <T : Any> T?.toResourceNullableSuccess(): Resource.Success<T?> {
    return Resource.Success(this)
}
