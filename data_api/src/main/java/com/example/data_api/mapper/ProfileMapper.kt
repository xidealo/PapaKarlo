package com.example.data_api.mapper

import com.bunbeauty.domain.model.profile.Profile
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.mapper.IProfileMapper
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.server.profile.get.ProfileServer
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
}