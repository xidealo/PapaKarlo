package com.bunbeauty.common

interface Mapper<T, E> {

    fun from(e: E): T

    fun to(t: T): E

    fun checkEmptyString(data: String): String? {
        if (data.isEmpty()) return null
        return data
    }
}