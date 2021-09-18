package com.example.domain_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.server.UserEmailServer
import com.example.domain_api.model.server.UserServer

interface IUserMapper {
    fun toEntityModel(user: UserServer): ProfileEntity
    fun toModel(user: ProfileEntity): User
    fun toUserEmailServer(user: User): UserEmailServer
}