package com.example.data_api.mapper

import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_api.mapper.IMenuProductMapper
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.model.entity.MenuProductEntity
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.entity.user.UserWithAddresses
import com.example.domain_api.model.server.MenuProductServer
import com.example.domain_api.model.server.UserServer
import javax.inject.Inject

class UserMapper @Inject constructor() : IUserMapper {

    override fun toEntityModel(user: UserServer, userUuid: String): UserWithAddresses {
        return UserWithAddresses(
            user = UserEntity(
                uuid = userUuid,
                phone = user.phone,
                email = user.email,
            ),
            emptyList()
            /* userAddressList = user.addressList.map { userAddressEntry ->
                *//* userAddressMapper.toEntityModel(
                    userAddressEntry.value,
                    userAddressEntry.key,
                    userUuid
                )*//*
            }*/
        )
    }

    override fun toUIModel(user: UserEntity): User {
        return User(
            uuid = user.uuid,
            phone = user.phone,
            email = user.email,
        )
    }
}