package com.bunbeauty.shared.data.mapper.addition

import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.domain.model.addition.Addition

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
        priority = priority
    )
}