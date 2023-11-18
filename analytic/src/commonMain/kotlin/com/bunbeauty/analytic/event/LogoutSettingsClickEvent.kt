package com.bunbeauty.analytic.event

class LogoutSettingsClickEvent(phone: String) :
    FoodDeliveryEvent(
        category = "settings",
        action = "LogoutSettingsClickEvent",
        params = listOf(
            EventParameter(
                key = "phone",
                value = phone
            )
        )
    )