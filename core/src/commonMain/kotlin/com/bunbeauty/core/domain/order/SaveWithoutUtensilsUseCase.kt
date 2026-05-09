package com.bunbeauty.core.domain.order

import com.bunbeauty.core.domain.repo.CreateOrderSettingsRepo

class SaveWithoutUtensilsUseCase(
    private val createOrderSettingsRepo: CreateOrderSettingsRepo,
) {
    suspend operator fun invoke(withoutUtensils: Boolean) {
        createOrderSettingsRepo.saveWithoutUtensils(withoutUtensils = withoutUtensils)
    }
}
