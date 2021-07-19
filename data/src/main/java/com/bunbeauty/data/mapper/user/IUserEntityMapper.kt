package com.bunbeauty.data.mapper.user

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.data.User
import com.bunbeauty.domain.model.entity.UserEntity

interface IUserEntityMapper: Mapper<UserEntity, User> {
}