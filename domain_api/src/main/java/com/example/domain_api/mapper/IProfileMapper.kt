package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Profile
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.ProfileEmailServer

interface IProfileMapper {
    fun toEntityModel(profile: ProfileServer): ProfileEntity
    fun toModel(user: ProfileEntity): Profile
    fun toUserEmailServer(profile: Profile): ProfileEmailServer
}