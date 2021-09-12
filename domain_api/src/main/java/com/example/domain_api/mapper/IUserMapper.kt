package com.example.domain_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.entity.user.UserWithAddresses
import com.example.domain_api.model.server.UserServer

interface IUserMapper {
    fun toEntityModel(user: UserServer, userUuid: String): UserWithAddresses
    fun toModel(user: UserEntity): User
}