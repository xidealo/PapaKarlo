package com.bunbeauty.papakarlo.data.model.discount

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.MenuProduct

data class Discount(
    @Embedded
    var discountEntity: DiscountEntity = DiscountEntity(),

    @Relation(parentColumn = "discountEntityUuid", entityColumn = "discountProductId")
    val discountProducts: List<DiscountProduct> = listOf(),

    @Relation(
        parentColumn = "discountEntityId",
        entityColumn = "uuid",
        associateBy = Junction(DiscountEntityMenuProduct::class)
    )
    val requiredProducts: List<MenuProduct> = listOf(),

)
