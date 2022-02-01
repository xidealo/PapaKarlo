package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.database.entity.user.ProfileEntity
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.Profile
import javax.inject.Inject

class ProfileMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val orderMapper: IOrderMapper,
    private val userMapper: IUserMapper,
) : IProfileMapper {

    override fun toProfileEntity(profileServer: ProfileServer): ProfileEntity {
        return ProfileEntity(
            user = userMapper.toEntityModel(profileServer),
            userAddressList = profileServer.addresses.map(userAddressMapper::toEntityModel),
            orderEntityList = profileServer.orders.map(orderMapper::toOrderEntityWithProducts)
        )
    }

    override fun toProfile(profileEntity: ProfileEntity): Profile {
        val lastOrderItem = profileEntity.orderEntityList.maxByOrNull { order ->
            order.orderEntity.time
        }?.let { order ->
            orderMapper.toLightOrder(order)
        }
        return Profile(
            userUuid = profileEntity.user.uuid,
            hasAddresses = profileEntity.userAddressList.isNotEmpty(),
            lastOrder = lastOrderItem
        )
    }
}