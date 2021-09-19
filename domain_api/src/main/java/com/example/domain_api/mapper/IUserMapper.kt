package com.example.domain_api.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.server.ProfileServer
import com.example.domain_api.model.server.UserEmailServer

interface IUserMapper {
    fun toEntityModel(profile: ProfileServer): ProfileEntity
    fun toModel(user: ProfileEntity): User
    fun toUserEmailServer(user: User): UserEmailServer
}