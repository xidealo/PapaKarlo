package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_api.model.entity.CafeEntity
import com.example.domain_api.model.entity.MenuProductEntity
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.entity.user.UserWithAddresses
import com.example.domain_api.model.server.CafeServer
import com.example.domain_api.model.server.MenuProductServer
import com.example.domain_api.model.server.UserServer

interface IUserMapper {
    fun toEntityModel(user: UserServer, userUuid: String): UserWithAddresses
    fun toUIModel(user: UserEntity): User
}