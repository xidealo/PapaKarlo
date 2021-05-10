package com.bunbeauty.domain.string_helper

import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.R
import com.bunbeauty.domain.resources.IResourcesProvider
import javax.inject.Inject

class StringHelper @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    IStringHelper {

    override fun toString(cafeAddress: CafeAddress?): String {
        if (cafeAddress == null) {
            return ""
        }

        return checkLastSymbol(
            "${cafeAddress.street?.name ?: "НЕТ"}, " +
                    "Дом: ${cafeAddress.house}, " +
                    flatString(cafeAddress) +
                    entranceString(cafeAddress) +
                    intercomString(cafeAddress) +
                    floorString(cafeAddress),
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

        if (orderEntity.deferred.isNotEmpty())
            orderString.append("Время доставки: ${orderEntity.deferred}\n")

        if (orderEntity.comment.isNotEmpty())
            orderString.append("Комментарий: ${orderEntity.comment}\n")

        if (orderEntity.email.isNotEmpty())
            orderString.append("Email: ${orderEntity.email}\n")

        orderString.append("Телефон: ${orderEntity.phone}")

        return orderString.toString()
    }

    override fun toStringOrderType(orderEntity: OrderEntity): String {
        return if (orderEntity.isDelivery)
            "Доставка"
        else
            "Самовывоз"
    }

    override fun toStringDeferred(orderEntity: OrderEntity): String {
        return if (orderEntity.deferred.isEmpty())
            "-"
        else
            orderEntity.deferred
    }

    override fun toStringComment(orderEntity: OrderEntity): String {
        return if (orderEntity.comment.isEmpty())
            "-"
        else
            orderEntity.comment
    }

    override fun toStringWeight(menuProduct: MenuProduct): String {
        return if (menuProduct.productCode == ProductCode.DRINK.name) {
            val liters = menuProduct.weight / 1000
            val milliliters = menuProduct.weight % 1000
            var volumeString = ""

            if (liters != 0) {
                volumeString += "$liters л "
            }
            if (milliliters != 0) {
                volumeString += "$milliliters мл"
            }

            volumeString
        } else {
            if (menuProduct.weight > 0) {
                "${menuProduct.weight} г"
            } else {
                ""
            }
        }
    }

    override fun toStringCost(cost: Int): String {
        return cost.toString() + resourcesProvider.getString(R.string.part_ruble)
    }

    override fun toStringTime(orderEntity: OrderEntity): String {
        return com.bunbeauty.data.model.Time(orderEntity.time, 3).toStringTimeHHMM()
    }

    override fun toStringTime(hours: Int?, minutes: Int?): String {
        return if (hours == null || minutes == null) {
            ""
        } else {
            hours.toString() + ":" + addFirstZero(minutes)
        }
    }

    override fun toStringWorkingHours(cafeEntity: CafeEntity): String {
        return "${cafeEntity.fromTime} - ${cafeEntity.toTime}"
    }

    fun checkLastSymbol(data: String, symbol: Char): String {
        if (data.trim().last() == symbol)
            return data.substringBeforeLast(symbol)

        return data
    }

    fun flatString(cafeAddress: CafeAddress): String {
        return if (cafeAddress.flat.isNotEmpty())
            "Квартира: ${cafeAddress.flat}, "
        else
            ""
    }

    fun entranceString(cafeAddress: CafeAddress): String {
        return if (cafeAddress.entrance.isNotEmpty())
            "Подъезд: ${cafeAddress.entrance}, "
        else
            ""
    }

    fun intercomString(cafeAddress: CafeAddress): String {
        return if (cafeAddress.intercom.isNotEmpty())
            "Домофон: ${cafeAddress.intercom}, "
        else
            ""
    }

    fun floorString(cafeAddress: CafeAddress): String {
        return if (cafeAddress.floor.isNotEmpty())
            "Этаж: ${cafeAddress.floor}"
        else
            ""
    }

    private fun addFirstZero(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else {
            number.toString()
        }
    }
}