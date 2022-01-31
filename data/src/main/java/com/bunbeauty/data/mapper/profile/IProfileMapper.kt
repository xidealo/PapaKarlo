package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.database.entity.user.ProfileEntity
import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.LightProfile

interface IProfileMapper {
    fun toEntityModel(profile: ProfileServer): ProfileEntity
    fun toLightProfile(profile: ProfileEntity): LightProfile
}