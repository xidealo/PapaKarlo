package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.addition.AdditionGroup

interface AdditionGroupRepo {
    suspend fun getAdditionGroup(uuid: String): AdditionGroup?
}
