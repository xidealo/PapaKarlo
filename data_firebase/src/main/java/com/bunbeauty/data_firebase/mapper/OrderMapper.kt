package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.model.address.UserAddress
import com.example.domain_firebase.mapper.ICafeMapper
import com.example.domain_firebase.mapper.IOrderMapper
import com.example.domain_firebase.mapper.IOrderProductMapper
import com.example.domain_firebase.mapper.IUserAddressMapper
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.order.OrderEntity
import com.example.domain_firebase.model.entity.order.OrderWithProducts
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
        val userAddressFirebase = order.userAddress?.let { userAddress ->
            userAddressMapper.toFirebaseModel(userAddress)
        }

        return OrderFirebase(
            orderEntity = OrderEntityFirebase(
                isDelivery = order.isDelivery,
                phone = order.phone,
                address = userAddressFirebase,
                comment = order.comment,
                deferredTime = order.deferredTime,
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
            userUuid = order.userUuid,
            phone = order.phone,
            cafeUuid = order.cafeUuid,
            comment = order.comment,
            deferredTime = order.deferredTime,
            time = order.time,
            code = order.code,
            orderStatus = order.orderStatus,
            userAddressStreet = order.userAddress?.street,
            userAddressHouse = order.userAddress?.house,
            userAddressFlat = order.userAddress?.flat,
            userAddressEntrance = order.userAddress?.entrance,
            userAddressFloor = order.userAddress?.floor,
            userAddressComment = order.userAddress?.comment
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
                street = userAddressStreet,
                house = userAddressHouse,
                flat = order.order.userAddressFlat,
                entrance = order.order.userAddressEntrance,
                floor = order.order.userAddressFloor,
                comment = order.order.userAddressComment,
                streetUuid = "",
                userUuid = order.order.userAddressComment
            )
        } else {
            null
        }

        return Order(
            uuid = order.order.uuid,
            isDelivery = order.order.isDelivery,
            userUuid = order.order.userUuid,
            phone = order.order.phone,
            userAddress = userAddress,
            cafeAddress = cafeMapper.toCafeAddress(cafe),
            comment = order.order.comment,
            deferredTime = order.order.deferredTime,
            time = order.order.time,
            code = order.order.code,
            orderStatus = order.order.orderStatus,
            orderProductList = order.orderProductList.map(orderProductMapper::toUIModel),
            cafeUuid = order.order.cafeUuid,
        )
    }


}