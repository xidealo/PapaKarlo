package com.bunbeauty.data.mapper.profile

import com.bunbeauty.data.network.model.profile.get.ProfileServer
import com.bunbeauty.domain.model.profile.Profile
import database.OrderEntity
import database.OrderWithProductEntity
import database.UserAddressEntity
import database.UserEntity

interface IProfileMapper {

    fun toProfile(
        userUuid: String,
        userAddressCount: Long,
        lastOrderEntity: OrderEntity?
    ): Profile

    fun toUserEntity(profileServer: ProfileServer): UserEntity
    fun toUserAddressEntityList(profileServer: ProfileServer): List<UserAddressEntity>
    fun toOrderWithProductEntityList(profileServer: ProfileServer): List<OrderWithProductEntity>

}