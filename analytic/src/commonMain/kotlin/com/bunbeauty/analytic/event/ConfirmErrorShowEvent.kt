package com.bunbeauty.analytic.event

class ConfirmErrorShowEvent(error: String) :
    FoodDeliveryEvent(
        category = "confirm",
        action = "ConfirmExceptionShowEvent",
        params = listOf(
            EventParameter(
                key = "error",
                value = error
            )
        )
    )