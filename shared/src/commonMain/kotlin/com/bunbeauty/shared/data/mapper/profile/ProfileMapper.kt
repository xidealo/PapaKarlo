package com.bunbeauty.shared.data.mapper.profile

import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.UserAddressEntity
import com.bunbeauty.shared.db.UserEntity
import com.bunbeauty.shared.domain.model.profile.Profile

class ProfileMapper(
    private val orderMapper: IOrderMapper,
    private val userAddressMapper: UserAddressMapper,
) : IProfileMapper {
    override fun toProfile(
        userUuid: String,
        userAddressCount: Long,
        lastOrderEntity: OrderEntity?,
    ): Profile.Authorized =
        Profile.Authorized(
            userUuid = userUuid,
            hasAddresses = userAddressCount > 0,
            lastOrder =
                lastOrderEntity?.let { orderEntity ->
                    orderMapper.toLightOrder(orderEntity)
                },
        )

    override fun toProfile(profileServer: ProfileServer): Profile.Authorized =
        Profile.Authorized(
            userUuid = profileServer.uuid,
            hasAddresses = profileServer.addresses.isNotEmpty(),
            lastOrder =
                profileServer.orders
                    .maxByOrNull { orderServer ->
                        orderServer.time
                    }?.let { orderServer ->
                        orderMapper.toLightOrder(orderServer)
                    },
        )

    override fun toUserEntity(profileServer: ProfileServer): UserEntity =
        UserEntity(
            uuid = profileServer.uuid,
            phone = profileServer.phoneNumber,
            email = profileServer.email,
        )

    override fun toUserAddressEntityList(profileServer: ProfileServer): List<UserAddressEntity> =
        profileServer.addresses.map { addressServer ->
            userAddressMapper.toUserAddressEntity(addressServer)
        }
}
