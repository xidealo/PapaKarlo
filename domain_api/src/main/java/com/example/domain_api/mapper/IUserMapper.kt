package com.example.domain_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.model.entity.user.UserWithAddresses
import com.example.domain_api.model.server.UserServer

interface IUserMapper {
    fun toEntityModel(user: UserServer): UserWithAddresses
    fun toModel(user: UserWithAddresses): User
}