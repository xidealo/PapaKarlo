package com.bunbeauty.core.model.mapper

import com.bunbeauty.core.Constants
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.product.MenuProduct

fun MenuProduct.toMenuProductItem(categoryUuid: String? = null): MenuItem.Product =
    MenuItem.Product(
        uuid = uuid,
        categoryUuid = categoryUuid,
        photoLink = photoLink,
        name = name,
        oldPrice =
            oldPrice?.let { oldPrice ->
                "$oldPrice ${Constants.RUBLE_CURRENCY}"
            },
        newPrice = "$newPrice ${Constants.RUBLE_CURRENCY}",
        hasAdditions = additionGroups.isNotEmpty(),
    )
