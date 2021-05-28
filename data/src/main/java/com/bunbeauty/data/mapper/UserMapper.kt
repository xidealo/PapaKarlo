package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.data.model.firebase.UserFirebase
import com.bunbeauty.data.model.user.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserFirebase, User> {

    override fun from(e: User): UserFirebase {
        return UserFirebase(
            phone = e.phone,
            email = e.email,
            bonusList = e.bonusList
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: UserFirebase): User {
        return User(
            phone = t.phone,
            email = t.email,
            bonusList = t.bonusList
        )
    }
}