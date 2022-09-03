package com.bunbeauty.shared.data.mapper.profile

import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderWithProductEntity
import com.bunbeauty.shared.db.UserAddressEntity
import com.bunbeauty.shared.db.UserEntity
import com.bunbeauty.shared.domain.model.profile.Profile

interface IProfileMapper {

    fun toProfile(
        userUuid: String,
        userAddressCount: Long,
        lastOrderEntity: OrderEntity?
    ): Profile.Authorized

    fun toProfile(profileServer: ProfileServer): Profile.Authorized

    fun toUserEntity(profileServer: ProfileServer): UserEntity
    fun toUserAddressEntityList(profileServer: ProfileServer): List<UserAddressEntity>
    fun toOrderWithProductEntityList(profileServer: ProfileServer): List<OrderWithProductEntity>

}