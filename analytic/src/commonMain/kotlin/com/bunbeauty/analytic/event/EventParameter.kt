package com.bunbeauty.analytic.event

open class EventParameter(
    val key: String,
    val value: String,
) {
    fun toPair() = Pair(key, value)
}

fun List<EventParameter>.toMap(): Map<Any?, String> = associate { it.toPair() }