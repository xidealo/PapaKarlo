package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.addition.AdditionGroup

class GetAdditionGroupsWithSelectedAdditionUseCase {
    operator fun invoke(
        additionGroups: List<AdditionGroup>,
        groupUuid: String,
        additionUuid: String,
    ) = additionGroups.map { additionGroup ->
        if (additionGroup.uuid == groupUuid) {
            additionGroup.copy(
                additionList = if (additionGroup.singleChoice) {
                    additionGroup.additionList.map { addition ->
                        addition.copy(isSelected = addition.uuid == additionUuid)
                    }
                } else {
                    additionGroup.additionList.map { addition ->
                        if (addition.uuid == additionUuid) {
                            addition.copy(isSelected = !addition.isSelected)
                        } else {
                            addition
                        }
                    }
                }
            )
        } else {
            additionGroup
        }
    }
}