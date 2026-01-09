package com.bunbeauty.shared.domain.repo

import com.bunbeauty.core.model.addition.Addition

interface AdditionRepo {
    suspend fun getAddition(uuid: String): Addition?
}
