package com.bunbeauty.data.mapper.user

import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.domain.model.profile.User
import database.UserEntity

interface IUserMapper {

    fun toEntityModel(profileServer: ProfileServer): UserEntity
    fun toUser(userEntity: UserEntity): User
    fun toUser(profileServer: ProfileServer): User
    fun toPatchServerModel(email: String): PatchUserServer
}