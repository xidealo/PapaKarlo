package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.Time
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import java.lang.StringBuilder
import javax.inject.Inject

class StringHelper @Inject constructor() : IStringHelper {

    override fun toString(address: Address): String {
        return checkLastSymbol(
            "${address.street?.name ?: "НЕТ"}, " +
                    "Дом: ${address.house}, " +
                    flatString(address) +
                    entranceString(address) +
                    intercomString(address) +
                    floorString(address),
            ','
        )
    }

    override fun toString(cartProducts: List<CartProduct>): String {
        var structure = ""

        for (cartProduct in cartProducts)
            structure += "${cartProduct.menuProduct.name} ${cartProduct.count}шт.; "

        return checkLastSymbol(
            "В заказе: \n${structure}",
            ';'
        )
    }

    override fun toString(orderEntity: OrderEntity): String {
        val orderString = StringBuilder()

        if (orderEntity.isDelivery)
            orderString.append("Доставка\n")
        else
            orderString.append("Самовывоз\n")

        orderString.append("Телефон: ${orderEntity.phone}\n")
        orderString.append("Комментарий: ${orderEntity.comment}\n")
        orderString.append("Email: ${orderEntity.email}")
        return orderString.toString()
    }

    override fun toStringCost(menuProduct: MenuProduct): String {
        return "${menuProduct.cost} ₽"
    }

    override fun toStringWeight(menuProduct: MenuProduct): String {
        return "${menuProduct.weight} г."
    }

    override fun toStringFullPrice(cartProduct: CartProduct): String {
        return (cartProduct.menuProduct.cost * cartProduct.count).toString() + " ₽"
    }

    override fun toStringFullPrice(order: Order): String {
        var fullPrice = 0
        for (cartProduct in order.cartProducts)
            fullPrice += cartProduct.count * cartProduct.menuProduct.cost

        return "Стоимость заказа: $fullPrice ₽"
    }

    override fun toStringTime(orderEntity: OrderEntity): String {
        return Time(orderEntity.time, 3).toStringTimeHHMM()
    }

    override fun toStringWorkingHours(cafeEntity: CafeEntity): String {
        return "${cafeEntity.fromTime} - ${cafeEntity.toTime}"
    }

    fun checkLastSymbol(data: String, symbol: Char): String {
        if (data.trim().last() == symbol)
            return data.substringBeforeLast(symbol)

        return data
    }

    fun flatString(address: Address): String {
        return if (address.flat.isNotEmpty())
            "Квартира: ${address.flat}, "
        else
            ""
    }

    fun entranceString(address: Address): String {
        return if (address.entrance.isNotEmpty())
            "Подъезд: ${address.entrance}, "
        else
            ""
    }

    fun intercomString(address: Address): String {
        return if (address.intercom.isNotEmpty())
            "Домофон: ${address.intercom}, "
        else
            ""
    }

    fun floorString(address: Address): String {
        return if (address.floor.isNotEmpty())
            "Этаж: ${address.floor}"
        else
            ""
    }
}