package com.bunbeauty.analytic.event

open class FoodDeliveryEvent(
    val category: String,
    val action: String,
    val params: List<EventParameter> = emptyList(),
) {
    fun eventName(prefix: String): String = "${prefix}_${category}_$action"

    companion object {
        const val MAX_EVENT_NAME_LENGTH = 40
        const val LONGEST_PREFIX = "vkuskavkaza"
    }
}