package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.addition.AdditionGroup

class GetAdditionPriorityUseCase {
    companion object {
        private const val ADDITION_GROUP_COEFFICIENT = 10
    }

    operator fun invoke(
        additionGroup: AdditionGroup,
        addition: Addition
    ) = additionGroup.priority * ADDITION_GROUP_COEFFICIENT + addition.priority
}
