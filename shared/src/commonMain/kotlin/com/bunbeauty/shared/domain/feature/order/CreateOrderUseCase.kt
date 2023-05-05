package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.CreatedOrderAddress
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.domain.util.IDateTimeUtil

class CreateOrderUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cartProductRepo: CartProductRepo,
    private val dateTimeUtil: IDateTimeUtil,
    private val orderRepo: OrderRepo,
) {

    suspend operator fun invoke(
        isDelivery: Boolean,
        selectedUserAddress: SelectableUserAddress?,
        selectedCafe: SelectableCafe?,
        comment: String?,
        deferredTime: Time?,
        timeZone: String,
    ): OrderCode? {
        val token = dataStoreRepo.getToken() ?: return null
        val cartProductList = cartProductRepo.getCartProductList()

        val createdOrderAddress = if (isDelivery) {
            selectedUserAddress ?: return null
            CreatedOrderAddress(
                uuid = selectedUserAddress.uuid,
                street = selectedUserAddress.street.name,
                house = selectedUserAddress.house,
                flat = selectedUserAddress.flat,
                entrance = selectedUserAddress.entrance,
                floor = selectedUserAddress.floor,
                comment = selectedUserAddress.comment,
            )
        } else {
            selectedCafe ?: return null
            CreatedOrderAddress(
                uuid = selectedCafe.uuid,
                description = selectedCafe.address
            )
        }

        val createdOrder = CreatedOrder(
            isDelivery = isDelivery,
            address = createdOrderAddress,
            comment = comment,
            deferredTime = deferredTime?.let {
                dateTimeUtil.getMillisByTime(deferredTime, timeZone)
            },
            orderProducts = cartProductList.map { cartProduct ->
                CreatedOrderProduct(
                    menuProductUuid = cartProduct.product.uuid,
                    count = cartProduct.count
                )
            }
        )

        return orderRepo.createOrder(token, createdOrder)
    }
}