package com.bunbeauty.shared.data.mapper.addition

import com.bunbeauty.core.model.addition.Addition
import com.bunbeauty.shared.db.AdditionEntity

val mapAdditionEntityToAddition: AdditionEntity.() -> Addition = {
    Addition(
        isSelected = isSelected,
        isVisible = isVisible,
        name = name,
        photoLink = photoLink,
        price = price,
        uuid = uuid,
        additionGroupUuid = additionGroupUuid,
        fullName = fullName,
        priority = priority,
    )
}
