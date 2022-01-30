package com.bunbeauty.papakarlo.util.string

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafePreview

interface IStringUtil {

    fun getCafeAddressString(cafeAddress: CafeAddress?): String
    fun getCafeAddressString(cafe: Cafe?): String?
    fun getUserAddressString(userAddress: UserAddress?): String?
    fun getWorkingHoursString(cafe: CafePreview): String
    fun getAddedToCartString(productName: String): String
    fun getRemovedFromCartString(productName: String): String
    fun getDeliveryCostString(deliveryCost: Int): String
    fun getCostString(cost: Int?): String
    fun getTimeString(hour: Int, minute: Int): String
    fun getCodeString(code: String): String
    fun getSizeString(weight: Int?): String
    fun getCountString(count: Int): String
    fun getOrderStatusString(orderStatus: OrderStatus): String
    fun getPickupMethodString(isDelivery: Boolean): String
}