package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.database.entity.user.ProfileEntity
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.LightProfile
import javax.inject.Inject

class ProfileMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val orderMapper: IOrderMapper,
    private val userMapper: IUserMapper,
) : IProfileMapper {

    override fun toEntityModel(profile: ProfileServer): ProfileEntity {
        return ProfileEntity(
            user = userMapper.toEntityModel(profile),
            userAddressList = profile.addresses.map(userAddressMapper::toEntityModel),
            orderEntityList = profile.orders.map(orderMapper::toOrderEntityWithProducts)
        )
    }

    override fun toLightProfile(profile: ProfileEntity): LightProfile {
        val lastOrderItem = profile.orderEntityList.maxByOrNull { order ->
            order.orderEntity.time
        }?.let { order ->
            orderMapper.toLightOrder(order)
        }
        return LightProfile(
            userUuid = profile.user.uuid,
            hasAddresses = profile.userAddressList.isNotEmpty(),
            lastOrder = lastOrderItem
        )
    }
}