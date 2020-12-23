package com.bunbeauty.papakarlo.data.model.order

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.BaseModel
import com.bunbeauty.papakarlo.data.model.CartProduct

data class OrderWithCartProducts(
    @Embedded
    var order: Order = Order(),

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    var cartProducts: List<CartProduct> = ArrayList()
) : BaseModel() {
    fun getFullPrice(): String {
        var fullPrice = 0
        for (cartProduct in cartProducts)
            fullPrice += cartProduct.count * cartProduct.menuProduct.cost

        return "Стоимость заказа: $fullPrice ₽"
    }


    fun getCartProductsStructure(): String {
        var structure = ""

        for (cartProduct in cartProducts)
            structure += "${cartProduct.menuProduct.name} ${cartProduct.count}шт.; "

        return "В заказе: $structure"
    }
}