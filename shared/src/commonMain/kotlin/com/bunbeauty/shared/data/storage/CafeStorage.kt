package com.bunbeauty.shared.data.storage

import com.bunbeauty.shared.domain.model.cafe.Cafe

class CafeStorage {
    private var userCafe: Cafe? = null

    fun setUserCafe(cafe: Cafe) {
        userCafe = cafe
    }

    fun getUserCafe(): Cafe? {
        return userCafe
    }

    fun clear() {
        userCafe = null
    }
}
