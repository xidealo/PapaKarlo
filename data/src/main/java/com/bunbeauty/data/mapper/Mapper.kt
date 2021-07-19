package com.bunbeauty.data.mapper

interface Mapper<FM, TM> {

    fun from(model: FM): TM

    fun to(model: TM): FM

    fun checkEmptyString(data: String): String? {
        if (data.isEmpty()) return null
        return data
    }

    fun checkEmptyInt(data: Int): Int? {
        if (data == 0) return null
        return data
    }
}