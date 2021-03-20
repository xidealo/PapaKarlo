package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.papakarlo.R
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import javax.inject.Inject

class StringHelper @Inject constructor(private val resourcesProvider: IResourcesProvider) : IStringHelper {

    override fun toString(address: com.bunbeauty.data.model.Address?): String {
        if (address == null) {
            return ""
        }

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

    override fun toString(cartProducts: List<com.bunbeauty.data.model.CartProduct>): String {
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

    override fun toStringWeight(menuProduct: com.bunbeauty.data.model.MenuProduct): String {
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

    override fun toStringPrice(price: Int): String {
        return price.toString() + resourcesProvider.getString(R.string.part_ruble)
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

    fun flatString(address: com.bunbeauty.data.model.Address): String {
        return if (address.flat.isNotEmpty())
            "Квартира: ${address.flat}, "
        else
            ""
    }

    fun entranceString(address: com.bunbeauty.data.model.Address): String {
        return if (address.entrance.isNotEmpty())
            "Подъезд: ${address.entrance}, "
        else
            ""
    }

    fun intercomString(address: com.bunbeauty.data.model.Address): String {
        return if (address.intercom.isNotEmpty())
            "Домофон: ${address.intercom}, "
        else
            ""
    }

    fun floorString(address: com.bunbeauty.data.model.Address): String {
        return if (address.floor.isNotEmpty())
            "Этаж: ${address.floor}"
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