package com.bunbeauty.shared.data.mapper.user

import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.db.UserEntity
import com.bunbeauty.shared.domain.model.profile.User

interface IUserMapper {

    fun toEntityModel(profileServer: ProfileServer): UserEntity
    fun toUser(userEntity: UserEntity): User
    fun toUser(profileServer: ProfileServer): User
    fun toPatchServerModel(email: String): PatchUserServer
}
