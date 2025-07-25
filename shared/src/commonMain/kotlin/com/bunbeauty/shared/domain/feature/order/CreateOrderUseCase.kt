package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.CreatedOrder
import com.bunbeauty.shared.domain.model.order.CreatedOrderAddress
import com.bunbeauty.shared.domain.model.order.OrderCode
import com.bunbeauty.shared.domain.model.product.CreatedOrderProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.domain.util.DateTimeUtil

class CreateOrderUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cartProductRepo: CartProductRepo,
    private val dateTimeUtil: DateTimeUtil,
    private val orderRepo: OrderRepo
) {

    suspend operator fun invoke(
        isDelivery: Boolean,
        selectedUserAddress: UserAddress?,
        selectedCafe: Cafe?,
        orderComment: String?,
        deferredTime: Time?,
        timeZone: String,
        paymentMethod: String?
    ): OrderCode? {
        val token = dataStoreRepo.getToken() ?: return null
        val cartProductList = cartProductRepo.getCartProductList()

        val createdOrderAddress = if (isDelivery) {
            selectedUserAddress ?: return null
            selectedUserAddress.run {
                CreatedOrderAddress(
                    uuid = uuid,
                    street = street,
                    house = house,
                    flat = flat,
                    entrance = entrance,
                    floor = floor,
                    comment = comment
                )
            }
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
            comment = orderComment,
            deferredTime = deferredTime?.let {
                dateTimeUtil.getMillisByTime(deferredTime, timeZone)
            },
            orderProducts = cartProductList.map { cartProduct ->
                CreatedOrderProduct(
                    menuProductUuid = cartProduct.product.uuid,
                    count = cartProduct.count,
                    additionUuids = getSortedAdditionUuidList(cartProduct)
                )
            },
            paymentMethod = paymentMethod
        )

        return orderRepo.createOrder(token = token, createdOrder = createdOrder)
    }

    private fun getSortedAdditionUuidList(cartProduct: CartProduct) =
        cartProduct.additionList.sortedBy { cartProductAddition ->
            cartProductAddition.priority
        }.map { cartProductAddition ->
            cartProductAddition.additionUuid
        }
}
