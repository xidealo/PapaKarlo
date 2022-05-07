package com.bunbeauty.data.mapper.user

import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.domain.model.profile.User
import database.UserEntity

class UserMapper : IUserMapper {

    override fun toEntityModel(profileServer: ProfileServer): UserEntity {
        return UserEntity(
            uuid = profileServer.uuid,
            phone = profileServer.phoneNumber,
            email = profileServer.email,
        )
    }

    override fun toUser(userEntity: UserEntity): User {
        return User(
            uuid = userEntity.uuid,
            phone = userEntity.phone,
            email = userEntity.email,
        )
    }

    override fun toUser(profileServer: ProfileServer): User {
        return User(
            uuid = profileServer.uuid,
            phone = profileServer.phoneNumber,
            email = profileServer.email,
        )
    }

    override fun toPatchServerModel(email: String): PatchUserServer {
        return PatchUserServer(email = email)
    }
}