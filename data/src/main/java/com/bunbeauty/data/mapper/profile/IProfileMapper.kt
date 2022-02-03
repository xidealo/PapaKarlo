package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.database.entity.user.ProfileEntity
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.Profile

interface IProfileMapper {
    fun toProfileEntity(profileServer: ProfileServer): ProfileEntity
    fun toProfile(profileEntity: ProfileEntity): Profile
}