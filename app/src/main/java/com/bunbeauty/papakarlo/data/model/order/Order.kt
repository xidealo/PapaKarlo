package com.bunbeauty.papakarlo.data.model.order

import androidx.room.Embedded
import androidx.room.Relation
import com.bunbeauty.papakarlo.data.model.BaseModel
import com.bunbeauty.papakarlo.data.model.CartProduct

data class Order(
    @Embedded
    var orderEntity: OrderEntity = OrderEntity(),

    @Relation(parentColumn = "id", entityColumn = "orderId")
    var cartProducts: List<CartProduct> = ArrayList()
) : BaseModel() {

    fun fullPrice(): String {
        var fullPrice = 0
        for (cartProduct in cartProducts)
            fullPrice += cartProduct.count * cartProduct.menuProduct.cost

        return "Стоимость заказа: $fullPrice ₽"
    }
}