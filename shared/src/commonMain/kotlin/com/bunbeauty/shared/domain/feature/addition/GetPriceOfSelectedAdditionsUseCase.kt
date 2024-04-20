package com.bunbeauty.shared.domain.feature.addition

import com.bunbeauty.shared.domain.model.addition.Addition

class GetPriceOfSelectedAdditionsUseCase {
    operator fun invoke(additions: List<Addition>) =
        additions.filter { addition ->
            addition.isSelected
        }.sumOf { addition ->
            addition.price ?: 0
        }
}