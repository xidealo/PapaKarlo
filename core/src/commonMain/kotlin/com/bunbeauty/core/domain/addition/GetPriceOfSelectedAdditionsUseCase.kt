package com.bunbeauty.core.domain.addition

import com.bunbeauty.core.model.addition.Addition

class GetPriceOfSelectedAdditionsUseCase {
    operator fun invoke(additions: List<Addition>) =
        additions
            .filter { addition ->
                addition.isSelected
            }.sumOf { addition ->
                addition.price ?: 0
            }
}
