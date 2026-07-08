package com.bunbeauty.core.domain.favorite

import com.bunbeauty.core.domain.menu_product.GetMenuProductListUseCase
import com.bunbeauty.core.domain.repo.FavoriteRepo
import com.bunbeauty.core.domain.repo.UserRepo
import com.bunbeauty.core.model.product.MenuProduct

class GetFavoriteMenuProductsUseCase(
    private val favoriteRepo: FavoriteRepo,
    private val getMenuProductListUseCase: GetMenuProductListUseCase,
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke(): List<MenuProduct> {
        if (userRepo.getToken() == null) {
            return emptyList()
        }

        val favoriteList = favoriteRepo.getFavoriteList()
        if (favoriteList.isEmpty()) {
            return emptyList()
        }

        val menuProductByUuid =
            getMenuProductListUseCase().associateBy { menuProduct ->
                menuProduct.uuid
            }

        return favoriteList
            .sortedByDescending { favorite ->
                favorite.createdAtMillis
            }.mapNotNull { favorite ->
                menuProductByUuid[favorite.menuProductUuid]
            }
    }
}
