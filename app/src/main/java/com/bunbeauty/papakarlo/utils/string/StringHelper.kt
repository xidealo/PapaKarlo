package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.order.Order
import javax.inject.Inject

class StringHelper @Inject constructor(): IStringHelper {

    override fun toString(address: Address): String {
        return checkLastSymbol(
            "Улица:${address.street}, " +
                    "Дом:${address.house}, " +
                    flatString(address) +
                    entranceString(address) +
                    intercomString(address) +
                    floorString(address),
            ','
        )
    }

    override fun toString(order: Order): String {
        var structure = ""

        for (cartProduct in order.cartProducts)
            structure += "${cartProduct.menuProduct.name} ${cartProduct.count}шт.; "

        return checkLastSymbol(
            "В заказе: \n${structure}",
            ';'
        )
    }

    fun checkLastSymbol(data: String, symbol: Char): String {
        if (data.trim().last() == symbol)
            return data.substringBeforeLast(symbol)

        return data
    }

    fun flatString(address: Address): String {
        return if (address.flat.isNotEmpty())
            "Квартира:${address.flat}, "
        else
            ""
    }

    fun entranceString(address: Address): String {
        return if (address.entrance.isNotEmpty())
            "Подъезд:${address.entrance}, "
        else
            ""
    }

    fun intercomString(address: Address): String {
        return if (address.intercom.isNotEmpty())
            "Домофон:${address.intercom}, "
        else
            ""
    }

    fun floorString(address: Address): String {
        return if (address.floor.isNotEmpty())
            "Этаж:${address.floor}"
        else
            ""
    }
}