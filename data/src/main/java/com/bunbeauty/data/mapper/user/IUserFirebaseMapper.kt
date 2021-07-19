package com.bunbeauty.data.mapper.user

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.entity.UserEntity
import com.bunbeauty.domain.model.firebase.UserFirebase

interface IUserFirebaseMapper: Mapper<UserFirebase, UserEntity> {
}