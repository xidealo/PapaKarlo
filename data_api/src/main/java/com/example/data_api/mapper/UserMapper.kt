package com.example.data_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.entity.user.UserWithAddresses
import com.example.domain_api.model.server.UserServer
import javax.inject.Inject

class UserMapper @Inject constructor() : IUserMapper {

    override fun toEntityModel(user: UserServer, userUuid: String): UserWithAddresses {
        return UserWithAddresses(
            user = UserEntity(
                uuid = userUuid,
                phone = user.phone,
                email = user.email,
            ),
            userAddressList = emptyList()
        )
    }

    override fun toModel(user: UserEntity): User {
        return User(
            uuid = user.uuid,
            phone = user.phone,
            email = user.email,
        )
    }
}