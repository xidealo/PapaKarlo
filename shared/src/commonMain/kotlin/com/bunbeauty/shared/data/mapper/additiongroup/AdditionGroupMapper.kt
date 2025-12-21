package com.bunbeauty.shared.data.mapper.additiongroup

import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.domain.model.addition.AdditionGroup

val mapAdditionGroupEntityToGroup: AdditionGroupEntity.() -> AdditionGroup =
    {
        AdditionGroup(
            uuid = uuid,
            name = name,
            priority = priority,
            additionList = emptyList(),
            isVisible = isVisible,
            singleChoice = singleChoice,
        )
    }
