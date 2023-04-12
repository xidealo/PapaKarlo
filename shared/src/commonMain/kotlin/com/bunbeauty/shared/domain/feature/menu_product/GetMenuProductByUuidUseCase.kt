package com.bunbeauty.shared.domain.feature.menu_product

import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo

class GetMenuProductByUuidUseCase(
    private val menuProductRepo: MenuProductRepo,
) {

    suspend operator fun invoke(menuProductUuid: String): MenuProduct? {
        return menuProductRepo.getMenuProductByUuid(menuProductUuid)
    }
}