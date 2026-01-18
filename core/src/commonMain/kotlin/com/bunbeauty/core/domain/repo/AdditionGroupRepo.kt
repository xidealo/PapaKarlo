package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.addition.AdditionGroup

interface AdditionGroupRepo {
    suspend fun getAdditionGroup(uuid: String): AdditionGroup?
}
