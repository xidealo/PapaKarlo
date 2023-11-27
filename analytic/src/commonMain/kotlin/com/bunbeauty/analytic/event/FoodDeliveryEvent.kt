package com.bunbeauty.analytic.event

open class FoodDeliveryEvent(
    val category: String,
    val action: String,
    val params: List<EventParameter> = emptyList(),
)