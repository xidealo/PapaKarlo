package com.bunbeauty.analytic.event.auth

import com.bunbeauty.analytic.event.FoodDeliveryEvent

class LoginRequestEvent :
    FoodDeliveryEvent(
        category = "auth",
        action = "login_req",
    )
