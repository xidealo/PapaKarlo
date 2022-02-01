package com.bunbeauty.data.network

data class ApiError(
    val code: Int = -1,
    override val message: String = ""
) : Throwable()
