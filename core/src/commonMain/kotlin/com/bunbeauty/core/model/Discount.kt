package com.bunbeauty.core.model

enum class DiscountSource {
    FIRST_ORDER,
    PERSONAL,
}

data class Discount(
    val firstOrderDiscount: Int?,
    val source: DiscountSource = DiscountSource.FIRST_ORDER,
)
