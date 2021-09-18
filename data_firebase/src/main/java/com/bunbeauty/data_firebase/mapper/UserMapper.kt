package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.model.User
import com.example.domain_firebase.mapper.IUserAddressMapper
import com.example.domain_firebase.mapper.IUserMapper
import com.example.domain_firebase.model.entity.user.UserEntity
import com.example.domain_firebase.model.entity.user.UserWithAddresses
import com.example.domain_firebase.model.firebase.UserFirebase
import javax.inject.Inject

class UserMapper @Inject constructor(private val userAddressMapper: IUserAddressMapper) :
    IUserMapper {

    override fun toEntityModel(user: UserFirebase, userUuid: String): UserWithAddresses {
        return UserWithAddresses(
            user = UserEntity(
                uuid = userUuid,
                phone = user.phone,
                email = user.email,
            ),
            userAddressList = user.addresses.map { userAddressEntry ->
                userAddressMapper.toEntityModel(
                    userAddressEntry.value,
                    userAddressEntry.key,
                    userUuid
                )
            }
        )
    }

    override fun toUIModel(user: UserEntity): User {
        return User(
            uuid = user.uuid,
            phone = user.phone,
            email = user.email,
            addressList = emptyList(),
            orderList = emptyList(),
        )
    }
}