package com.bunbeauty.domain.string_helper

import com.bunbeauty.data.enums.OrderStatus
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.address.Address
import com.bunbeauty.domain.R
import com.bunbeauty.domain.resources.IResourcesProvider
import javax.inject.Inject

class StringHelper @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    IStringHelper {

    override fun toString(address: Address?): String {
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

        if (orderEntity.deferredTime.isNotEmpty())
            orderString.append("Время доставки: ${orderEntity.deferredTime}\n")

        if (orderEntity.comment.isNotEmpty())
            orderString.append("Комментарий: ${orderEntity.comment}\n")

        if (orderEntity.email.isNotEmpty())
            orderString.append("Email: ${orderEntity.email}\n")

        orderString.append("Телефон: ${orderEntity.phone}")

        return orderString.toString()
    }

    override fun toStringIsDelivery(orderEntity: OrderEntity): String {
        return if (orderEntity.isDelivery)
            "Доставка"
        else
            "Самовывоз"
    }

    override fun toStringDeferred(orderEntity: OrderEntity): String {
        return if (orderEntity.deferredTime.isEmpty())
            "-"
        else
            orderEntity.deferredTime
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

    override fun getCostString(cost: Int?): String {
        return if (cost == null) {
            ""
        } else {
            cost.toString() + resourcesProvider.getString(R.string.part_ruble)
        }
    }

    override fun toStringOrderStatus(orderStatus: OrderStatus): String {
        return when (orderStatus) {
            OrderStatus.NOT_ACCEPTED -> resourcesProvider.getString(R.string.msg_status_not_accepted)
            OrderStatus.ACCEPTED -> resourcesProvider.getString(R.string.msg_status_accepted)
            OrderStatus.PREPARING -> resourcesProvider.getString(R.string.msg_status_preparing)
            OrderStatus.SENT_OUT -> resourcesProvider.getString(R.string.msg_status_sent_out)
            OrderStatus.DELIVERED -> resourcesProvider.getString(R.string.msg_status_delivered)
            OrderStatus.DONE -> resourcesProvider.getString(R.string.msg_status_ready)
            OrderStatus.CANCELED -> resourcesProvider.getString(R.string.msg_status_canceled)
        }
    }

    override fun getDeliveryString(deliveryCost: Int): String {
        return if (deliveryCost == 0) {
            resourcesProvider.getString(R.string.msg_order_details_delivery_free)
        } else {
            getCostString(deliveryCost)
        }
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

    private fun addFirstZero(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else {
            number.toString()
        }
    }
}