package com.example.data_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.mapper.IOrderMapper
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.server.UserEmailServer
import com.example.domain_api.model.server.UserServer
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val orderMapper: IOrderMapper,
) : IUserMapper {

    override fun toEntityModel(user: UserServer): ProfileEntity {
        return ProfileEntity(
            user = UserEntity(
                uuid = user.uuid,
                phone = user.phone,
                email = user.email,
            ),
            userAddressList = user.addressList.map(userAddressMapper::toEntityModel),
            orderList = user.orderList.map(orderMapper::toEntityModel)
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