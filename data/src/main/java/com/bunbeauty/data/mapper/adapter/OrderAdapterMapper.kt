package com.bunbeauty.data.mapper.adapter

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.adapter.MenuProductAdapterModel
import com.bunbeauty.domain.model.adapter.OrderAdapterModel
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import javax.inject.Inject

class OrderAdapterMapper @Inject constructor(
    private val iStringHelper: IStringHelper,
    private val orderUtil: IOrderUtil
) : Mapper<OrderAdapterModel, Order> {

    override fun from(e: Order): OrderAdapterModel {
        return OrderAdapterModel(
            uuid = e.orderEntity.uuid,
            orderStatus = iStringHelper.toStringOrderStatus(e.orderEntity.orderStatus),
            orderColor = orderUtil.getBackgroundColor(e.orderEntity.orderStatus),
            code = e.orderEntity.code,
            time = iStringHelper.toStringTime(e.orderEntity),
            deferredTime = if (e.orderEntity.deferredTime.isNotEmpty())
                "Ко времени: ${e.orderEntity.deferredTime}"
            else
                ""
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: OrderAdapterModel): Order {
        return Order()
    }
}