package com.bunbeauty.shared.presentation.consumercart.mapper

import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.model.cart.LightCartProduct
import com.bunbeauty.shared.presentation.consumercart.CartProductItem

fun LightCartProduct.toCartProductItem(): CartProductItem =
    CartProductItem(
        uuid = uuid,
        name = name,
        newCost = "$newCost $RUBLE_CURRENCY",
        oldCost =
            oldCost?.let { oldCost ->
                "$oldCost $RUBLE_CURRENCY"
            },
        photoLink = photoLink,
        count = count,
        menuProductUuid = menuProductUuid,
        additions =
            cartProductAdditionList
                .joinToString(" • ") { cartProductAddition ->
                    cartProductAddition.fullName ?: cartProductAddition.name
                }.ifEmpty { null },
        additionUuidList =
            cartProductAdditionList.map { cartProductAddition ->
                cartProductAddition.additionUuid
            },
    )
