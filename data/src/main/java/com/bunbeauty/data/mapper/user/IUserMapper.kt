package com.bunbeauty.data.mapper.user

import com.bunbeauty.data.database.entity.user.UserEmailUpdate
import com.bunbeauty.data.database.entity.user.UserEntity
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.domain.model.profile.User

interface IUserMapper {

    fun toEntityModel(profile: ProfileServer): UserEntity
    fun toModel(user: UserEntity): User
    fun toModel(profile: ProfileServer): User
    fun toPatchServerModel(email: String): PatchUserServer
    fun toUserUpdate(profile: ProfileServer): UserEmailUpdate
}