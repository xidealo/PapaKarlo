package com.bunbeauty.data.mapper.user

import com.bunbeauty.data.database.entity.user.UserEmailUpdate
import com.bunbeauty.data.database.entity.user.UserEntity
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.domain.model.profile.User

class UserMapper  constructor() : IUserMapper {

    override fun toEntityModel(profile: ProfileServer): UserEntity {
        return UserEntity(
            uuid = profile.uuid,
            phone = profile.phoneNumber,
            email = profile.email,
        )
    }

    override fun toModel(user: UserEntity): User {
        return User(
            uuid = user.uuid,
            phone = user.phone,
            email = user.email,
        )
    }

    override fun toModel(profile: ProfileServer): User {
        return User(
            uuid = profile.uuid,
            phone = profile.phoneNumber,
            email = profile.email,
        )
    }

    override fun toPatchServerModel(email: String): PatchUserServer {
        return PatchUserServer(email = email)
    }

    override fun toUserUpdate(profile: ProfileServer): UserEmailUpdate {
        return UserEmailUpdate(
            uuid = profile.uuid,
            email = profile.email
        )
    }
}