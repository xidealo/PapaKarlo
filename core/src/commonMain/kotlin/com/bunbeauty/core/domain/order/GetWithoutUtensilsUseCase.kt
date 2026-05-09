package com.bunbeauty.core.domain.order

import com.bunbeauty.core.domain.repo.CreateOrderSettingsRepo

class GetWithoutUtensilsUseCase(
    private val createOrderSettingsRepo: CreateOrderSettingsRepo,
) {
    suspend operator fun invoke(): Boolean = createOrderSettingsRepo.getWithoutUtensils()
}
