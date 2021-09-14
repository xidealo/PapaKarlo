package com.example.data_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.entity.user.UserWithAddresses
import com.example.domain_api.model.server.UserServer
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper
) : IUserMapper {

    override fun toEntityModel(user: UserServer): UserWithAddresses {
        return UserWithAddresses(
            user = UserEntity(
                uuid = user.uuid,
                phone = user.phone,
                email = user.email,
            ),
            userAddressList = user.addressList.map(userAddressMapper::toEntityModel)
        )
    }

    override fun toModel(user: UserWithAddresses): User {
        return User(
            uuid = user.user.uuid,
            phone = user.user.phone,
            email = user.user.email,
            addressList = user.userAddressList.map(userAddressMapper::toModel)
        )
    }
}