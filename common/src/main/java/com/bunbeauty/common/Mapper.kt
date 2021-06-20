package com.bunbeauty.common

interface Mapper<T, E> {

    fun from(e: E): T

    fun to(t: T): E

    fun checkEmptyString(data: String): String? {
        if (data.isEmpty()) return null
        return data
    }

    fun checkEmptyInt(data: Int): Int? {
        if (data == 0) return null
        return data
    }
}