package com.example.domain_api.mapper

import com.bunbeauty.domain.model.profile.User
import com.example.domain_api.model.entity.user.UserEmailUpdate
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.PatchUserServer

interface IUserMapper {

    fun toEntityModel(profile: ProfileServer): UserEntity
    fun toModel(user: UserEntity): User
    fun toModel(profile: ProfileServer): User
    fun toPatchServerModel(email: String): PatchUserServer
    fun toUserUpdate(profile: ProfileServer): UserEmailUpdate
}