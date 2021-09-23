package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_firebase.mapper.ICafeMapper
import com.example.domain_firebase.mapper.IOrderMapper
import com.example.domain_firebase.mapper.IOrderProductMapper
import com.example.domain_firebase.mapper.IUserAddressMapper
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.order.OrderEntity
import com.example.domain_firebase.model.entity.order.OrderWithProducts
import com.example.domain_firebase.model.firebase.address.UserAddressFirebase
import com.example.domain_firebase.model.firebase.order.OrderEntityFirebase
import com.example.domain_firebase.model.firebase.order.OrderFirebase
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val orderProductMapper: IOrderProductMapper,
    private val cafeMapper: ICafeMapper
) : IOrderMapper {

    override fun toFirebaseModel(order: Order): OrderFirebase {
        val userAddressFirebase = UserAddressFirebase()

        return OrderFirebase(
            orderEntity = OrderEntityFirebase(
                isDelivery = order.isDelivery,
                address = userAddressFirebase,
                comment = order.comment,
                deferredTime = order.deferredTime.toString(),
                spentBonuses = 0,
                accruedBonuses = 0,
                orderStatus = OrderStatus.NOT_ACCEPTED,
                code = order.code,
                time = order.time,
                userUuid = order.userUuid,
            ),
            cartProducts = order.orderProductList.map(orderProductMapper::toFirebaseModel)
        )
    }

    override fun toEntityModel(order: Order): OrderEntity {
        return OrderEntity(
            uuid = order.uuid,
            isDelivery = order.isDelivery,
            phone = "",
            cafeUuid = "",
            userUuid = order.userUuid,
            comment = order.comment,
            deferredTime = order.deferredTime.toString(),
            time = order.time,
            code = order.code,
            orderStatus = order.orderStatus,
            userAddressStreet = order.address,
            userAddressHouse = null,
            userAddressFlat = null,
            userAddressEntrance = null,
            userAddressFloor = null,
            userAddressComment = null,
        )
    }

    override fun toEntityModel(
        order: OrderFirebase,
        userOrderFirebase: UserOrderFirebase,
        userUuid: String
    ): OrderWithProducts {
        return OrderWithProducts(
            order = OrderEntity(
                uuid = userOrderFirebase.orderUuid,
                isDelivery = order.orderEntity.isDelivery,
                phone = order.orderEntity.phone,
                comment = order.orderEntity.comment,
                deferredTime = order.orderEntity.deferredTime,
                time = order.orderEntity.time,
                code = order.orderEntity.code,
                orderStatus = order.orderEntity.orderStatus,
                cafeUuid = userOrderFirebase.cafeUuid,
                userUuid = userUuid,
                userAddressStreet = order.orderEntity.address?.street?.name,
                userAddressHouse = order.orderEntity.address?.house,
                userAddressFlat = order.orderEntity.address?.flat,
                userAddressEntrance = order.orderEntity.address?.entrance,
                userAddressFloor = order.orderEntity.address?.floor,
                userAddressComment = order.orderEntity.address?.comment,
            ),
            orderProductList = order.cartProducts.map { cartProductFirebase ->
                orderProductMapper.toEntityModel(cartProductFirebase, userOrderFirebase.orderUuid)
            }
        )
    }

    override fun toUIModel(order: OrderWithProducts, cafe: CafeEntity): Order {
        val userAddressStreet = order.order.userAddressStreet
        val userAddressHouse = order.order.userAddressHouse
        val userAddress = if (userAddressStreet != null && userAddressHouse != null) {
            UserAddress(
                uuid = "",
                street = Street(
                    uuid = "",
                    name = "",
                    cityUuid = "",
                ),
                house = userAddressHouse,
                flat = order.order.userAddressFlat,
                entrance = order.order.userAddressEntrance,
                floor = order.order.userAddressFloor,
                comment = order.order.userAddressComment,
                userUuid = order.order.userAddressComment
            )
        } else {
            null
        }

        return Order(
            uuid = order.order.uuid,
            isDelivery = order.order.isDelivery,
            userUuid = order.order.userUuid,
            address = userAddress.toString(),
            comment = order.order.comment,
            deferredTime = null,
            time = order.order.time,
            code = order.order.code,
            orderStatus = order.order.orderStatus,
            orderProductList = order.orderProductList.map(orderProductMapper::toUIModel),
            addressUuid = null
        )
    }


}