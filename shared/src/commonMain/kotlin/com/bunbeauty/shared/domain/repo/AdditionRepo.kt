package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.addition.Addition

interface AdditionRepo {
    suspend fun getAddition(uuid: String): Addition?
}
