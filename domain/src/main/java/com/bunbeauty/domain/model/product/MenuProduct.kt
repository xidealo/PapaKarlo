package com.bunbeauty.domain.model.product

import com.bunbeauty.domain.model.category.Category

data class MenuProduct(
    val uuid: String,
    val name: String,
    override val newPrice: Int,
    override val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val categoryList: List<Category>
) : Product()