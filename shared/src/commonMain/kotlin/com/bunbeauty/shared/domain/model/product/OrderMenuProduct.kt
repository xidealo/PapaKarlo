package com.bunbeauty.shared.domain.model.product

data class OrderMenuProduct(
    val name: String,
    override val newPrice: Int,
    override val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String
) : Product()