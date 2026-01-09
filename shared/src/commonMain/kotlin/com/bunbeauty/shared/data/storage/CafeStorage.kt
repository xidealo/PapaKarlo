package com.bunbeauty.shared.data.storage

import com.bunbeauty.core.model.cafe.Cafe

class CafeStorage {
    private var userCafe: Cafe? = null

    fun setUserCafe(cafe: Cafe) {
        userCafe = cafe
    }

    fun getUserCafe(): Cafe? = userCafe

    fun clear() {
        userCafe = null
    }
}
