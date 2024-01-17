package com.bunbeauty.shared.data.mapper.additiongroup

import com.bunbeauty.shared.data.mapper.addition.mapAdditionEntityToAddition
import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.domain.model.addition.AdditionGroup

val mapAdditionGroupEntityToGroup: AdditionGroupEntity.(additionList: List<AdditionEntity>) -> AdditionGroup =
    { additionList ->
        AdditionGroup(
            uuid = uuid,
            name = name,
            priority = priority,
            additionList = additionList.mapNotNull(mapAdditionEntityToAddition),
            isVisible = isVisible,
            singleChoice = singleChoice
        )
    }
