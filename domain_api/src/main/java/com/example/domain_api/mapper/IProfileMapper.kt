package com.example.domain_api.mapper

import com.bunbeauty.domain.model.profile.Profile
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.server.profile.get.ProfileServer

interface IProfileMapper {
    fun toEntityModel(profile: ProfileServer): ProfileEntity
    fun toModel(profile: ProfileEntity): Profile
}