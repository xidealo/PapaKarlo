package com.bunbeauty.data.network

data class ApiError(
    val code: Int = -1,
    override val message: String = ""
) : Throwable() {

    companion object {
        val DATA_IS_NULL = ApiError(code = 0, "Data is null")
    }
}
