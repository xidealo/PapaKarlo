package com.bunbeauty.analytic.event

data class EventParameter(
    val key: String,
    val value: String,
) {
    fun toPair() = Pair(key, value)
}

fun List<EventParameter>.toMap(): Map<String, Any> = associate { it.toPair() }