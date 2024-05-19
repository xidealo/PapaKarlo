package com.bunbeauty.shared.presentation.menu.model

sealed class MenuItem {
    data class CategoryHeader(
        val uuid: String,
        val name: String,
    ) : MenuItem()

    data class Product(
        val uuid: String,
        val categoryUuid: String?,
        val photoLink: String,
        val name: String,
        val oldPrice: String?,
        val newPrice: String,
        val hasAdditions: Boolean
    ) : MenuItem()

    data class Discount(
        val discount: String
    ) : MenuItem()
}
