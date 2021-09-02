package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.user.UserEntity
import com.bunbeauty.domain.model.entity.user.UserWithAddresses
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.ui.User

interface IUserMapper {

    fun toEntityModel(user: UserFirebase, userUuid: String): UserWithAddresses
    fun toUIModel(user: UserEntity): User
}