package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.MENU_PRODUCT_TAG
import com.bunbeauty.data.dao.menu_product.IMenuProductDao
import com.bunbeauty.data.dao.menu_product_category_reference.IMenuProductCategoryReferenceDao
import com.bunbeauty.data.handleListResult
import com.bunbeauty.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuProductRepository constructor(
    private val apiRepository: ApiRepo,
    private val menuProductDao: IMenuProductDao,
    private val menuProductCategoryReferenceDao: IMenuProductCategoryReferenceDao,
    private val menuProductMapper: IMenuProductMapper
) : MenuProductRepo {

    override suspend fun refreshMenuProductList() {
        apiRepository.getMenuProductList()
            .handleListResult(MENU_PRODUCT_TAG) { menuProductServerList ->
                if (menuProductServerList != null) {
                    val menuProductEntityList =
                        menuProductServerList.map(menuProductMapper::toMenuProductEntity)
                    menuProductDao.insertMenuProductList(menuProductEntityList)

                    val menuProductCategoryReferenceList =
                        menuProductServerList.flatMap(menuProductMapper::toMenuProductCategoryReference)
                    menuProductCategoryReferenceDao.updateMenuProductReferenceList(
                        menuProductCategoryReferenceList
                    )
                }
            }
    }

    override fun observeMenuProductList(): Flow<List<MenuProduct>> {
        return menuProductDao.observeMenuProductList().map { menuProductWithCategoryEntityList ->
            menuProductMapper.toMenuProductList(menuProductWithCategoryEntityList)
        }
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductDao.observeMenuProductByUuid(menuProductUuid)
            .mapFlow(menuProductMapper::toMenuProduct)
    }
}