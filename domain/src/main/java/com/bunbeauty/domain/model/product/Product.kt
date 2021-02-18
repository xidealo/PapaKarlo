package com.bunbeauty.domain.model.product

abstract class Product {
    abstract val newPrice: Int
    abstract val oldPrice: Int?
}