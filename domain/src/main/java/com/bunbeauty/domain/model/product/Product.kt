package com.bunbeauty.domain.model.product

abstract class Product {
    abstract val cost: Int
    abstract val discountCost: Int?
}