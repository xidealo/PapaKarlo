package com.example.domain_api.mapper

import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.ProfileEmailServer

interface IProfileMapper {
    fun toEntityModel(profile: ProfileServer): ProfileEntity
    fun toModel(profile: ProfileEntity): Profile
    fun toModel(profile: ProfileServer): Profile
    fun toUserEmailServer(user: User): ProfileEmailServer
}