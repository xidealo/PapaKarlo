package com.bunbeauty.papakarlo.utils.string

import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.order.Order

interface IStringHelper {
    fun toString(address: Address):String
    fun toString(order: Order):String
}