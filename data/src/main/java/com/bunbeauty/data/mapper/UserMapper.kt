package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.IUserAddressMapper
import com.bunbeauty.domain.mapper.IUserMapper
import com.bunbeauty.domain.model.entity.user.UserEntity
import com.bunbeauty.domain.model.entity.user.UserWithAddresses
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.ui.User
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
        )
    }
}