package com.bunbeauty.shared.data.mapper.additiongroup

import com.bunbeauty.core.model.addition.AdditionGroup
import com.bunbeauty.shared.db.AdditionGroupEntity

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
