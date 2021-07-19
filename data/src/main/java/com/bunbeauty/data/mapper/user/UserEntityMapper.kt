package com.bunbeauty.data.mapper.user

import com.bunbeauty.domain.model.data.User
import com.bunbeauty.domain.model.entity.UserEntity
import javax.inject.Inject

class UserEntityMapper @Inject constructor() : IUserEntityMapper {

    override fun from(model: UserEntity): User {
        return User(
            uuid = model.uuid,
            phone = model.phone,
            email = model.email
        )
    }

    override fun to(model: User): UserEntity {
        return UserEntity(
            uuid = model.uuid,
            phone = model.phone,
            email = model.email
        )
    }


}