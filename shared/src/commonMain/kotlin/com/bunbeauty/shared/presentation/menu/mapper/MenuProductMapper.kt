package com.bunbeauty.shared.presentation.menu.mapper

import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.presentation.menu.model.MenuItem

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
