package com.example.data_api.mapper

import com.bunbeauty.domain.model.profile.User
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.user.UserEmailUpdate
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.PatchUserServer
import javax.inject.Inject

class UserMapper @Inject constructor() : IUserMapper {

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