package com.bunbeauty.domain.model.product

abstract class ProductPosition {
    abstract val uuid: String
    abstract val count: Int
    abstract val menuProduct: MenuProduct
}