package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.database.entity.user.ProfileEntity
import com.bunbeauty.data.mapper.order.IOrderMapper
import com.bunbeauty.data.mapper.user.IUserMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.LightProfile
import com.bunbeauty.domain.model.profile.Profile
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
            orderList = profile.orders.map(orderMapper::toEntityModel)
        )
    }

    override fun toModel(profile: ProfileEntity): Profile {
        return Profile(
            user = userMapper.toModel(profile.user),
            addressList = profile.userAddressList.map(userAddressMapper::toModel),
            orderList = profile.orderList.map(orderMapper::toModel),
        )
    }

    override fun toLightProfile(profile: ProfileEntity): LightProfile {
        val lastOrderItem = profile.orderList.maxByOrNull { order ->
            order.order.time
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