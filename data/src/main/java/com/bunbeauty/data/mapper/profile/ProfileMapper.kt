package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.Profile
import database.OrderEntity
import database.OrderWithProductEntity
import database.UserAddressEntity
import database.UserEntity

class ProfileMapper(
    private val orderMapper: IOrderMapper,
    private val userAddressMapper: IUserAddressMapper
) : IProfileMapper {

    override fun toProfile(
        userUuid: String,
        userAddressCount: Long,
        lastOrderEntity: OrderEntity?
    ): Profile {
        return Profile(
            userUuid = userUuid,
            hasAddresses = userAddressCount > 0,
            lastOrder = lastOrderEntity?.let { orderEntity ->
                orderMapper.toLightOrder(orderEntity)
            }
        )
    }

    override fun toUserEntity(profileServer: ProfileServer): UserEntity {
        return UserEntity(
            uuid = profileServer.uuid,
            phone = profileServer.phoneNumber,
            email = profileServer.email,
        )
    }

    override fun toUserAddressEntityList(profileServer: ProfileServer): List<UserAddressEntity> {
        return profileServer.addresses.map { addressServer ->
            userAddressMapper.toUserAddressEntity(addressServer)
        }
    }

    override fun toOrderWithProductEntityList(profileServer: ProfileServer): List<OrderWithProductEntity> {
        return profileServer.orders.flatMap { orderServer ->
            orderMapper.toOrderWithProductEntityList(orderServer)
        }
    }
}