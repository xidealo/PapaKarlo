package com.bunbeauty.core.domain.addition

import com.bunbeauty.core.model.addition.Addition
import com.bunbeauty.core.model.addition.AdditionGroup


interface GetAdditionPriorityUseCase {
    operator fun invoke(
        additionGroup: AdditionGroup,
        addition: Addition,
    ): Int
}

class GetAdditionPriorityUseCaseImpl : GetAdditionPriorityUseCase {
    companion object {
        private const val ADDITION_GROUP_COEFFICIENT = 10
    }

    override operator fun invoke(
        additionGroup: AdditionGroup,
        addition: Addition,
    ) = additionGroup.priority * ADDITION_GROUP_COEFFICIENT + addition.priority
}
