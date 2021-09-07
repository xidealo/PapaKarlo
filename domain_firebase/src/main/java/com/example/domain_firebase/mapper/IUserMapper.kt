package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_firebase.model.entity.user.UserEntity
import com.example.domain_firebase.model.entity.user.UserWithAddresses
import com.example.domain_firebase.model.firebase.UserFirebase

interface IUserMapper {

    fun toEntityModel(user: UserFirebase, userUuid: String): UserWithAddresses
    fun toUIModel(user: UserEntity): User
}