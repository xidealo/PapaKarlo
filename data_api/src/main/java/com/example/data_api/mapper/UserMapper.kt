package com.example.data_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.server.ProfileServer
import com.example.domain_api.model.server.UserEmailServer
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val orderMapper: IOrderMapper,
) : IUserMapper {

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

    override fun toModel(user: ProfileEntity): User {
        return User(
            uuid = user.user.uuid,
            phone = user.user.phone,
            email = user.user.email,
            addressList = user.userAddressList.map(userAddressMapper::toModel),
            orderList = user.orderList.map(orderMapper::toModel),
        )
    }

    override fun toUserEmailServer(user: User): UserEmailServer {
        return UserEmailServer(
            email = user.email
        )
    }
}