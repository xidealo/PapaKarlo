package com.example.data_api.mapper

import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.mapper.IProfileMapper
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.ProfileEmailServer
import javax.inject.Inject

class ProfileMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val orderMapper: IOrderMapper,
) : IProfileMapper {

    override fun toEntityModel(profile: ProfileServer): ProfileEntity {
        return ProfileEntity(
            user = UserEntity(
                uuid = profile.uuid,
                phone = profile.phone,
                email = profile.email,
            ),
            userAddressList = profile.addressList.map(userAddressMapper::toEntityModel),
            orderList = profile.orderList.map(orderMapper::toEntityModel)
        )
    }

    override fun toModel(profile: ProfileEntity): Profile {
        return Profile(
            user = User(
                uuid = profile.user.uuid,
                phone = profile.user.phone,
                email = profile.user.email,
            ),
            addressList = profile.userAddressList.map(userAddressMapper::toModel),
            orderList = profile.orderList.map(orderMapper::toModel),
        )
    }

    override fun toModel(profile: ProfileServer): Profile {
        return Profile(
            user = User(
                uuid = profile.uuid,
                phone = profile.phone,
                email = profile.email,
            ),
            addressList = profile.addressList.map(userAddressMapper::toModel),
            orderList = profile.orderList.map(orderMapper::toModel),
        )
    }

    override fun toUserEmailServer(user: User): ProfileEmailServer {
        return ProfileEmailServer(
            email = user.email
        )
    }
}