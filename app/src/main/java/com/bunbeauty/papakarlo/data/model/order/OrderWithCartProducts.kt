package com.bunbeauty.papakarlo.data.model.order

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.BaseModel
import com.bunbeauty.papakarlo.data.model.CartProduct

data class OrderWithCartProducts(
    @Embedded
    var order: Order = Order(),

    @Relation(parentColumn = "uuid", entityColumn = "orderUuid")
    var cartProducts: MutableList<CartProduct> = ArrayList()
) : BaseModel() {
    fun getFullPrice(): String {
        var fullPrice = 0
        for (cartProduct in cartProducts)
            fullPrice += cartProduct.count * cartProduct.menuProduct.cost

        return "Стоимость заказа: $fullPrice ₽"
    }
}