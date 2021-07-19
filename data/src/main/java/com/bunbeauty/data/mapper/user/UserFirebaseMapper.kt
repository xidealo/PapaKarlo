package com.bunbeauty.data.mapper.user

import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.entity.UserEntity
import javax.inject.Inject

class UserFirebaseMapper @Inject constructor() :  IUserFirebaseMapper {

    override fun from(model: UserFirebase): UserEntity {
        return UserEntity(
            phone = model.phone,
            email = model.email,
            bonusList = model.bonusList
        )
    }

    override fun to(model: UserEntity): UserFirebase {
        return UserFirebase(
            phone = model.phone,
            email = model.email,
            bonusList = model.bonusList
        )
    }
}