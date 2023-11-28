package com.bunbeauty.shared.domain.model.product

import com.bunbeauty.shared.domain.model.category.Category

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
    val categoryList: List<Category>,
    val visible: Boolean,
    val isRecommended: Boolean,
) : Product()