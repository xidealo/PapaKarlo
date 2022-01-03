package com.example.data_api.repository

import com.bunbeauty.common.Logger.MENU_PRODUCT_TAG
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import com.example.data_api.dao.MenuProductDao
import com.example.data_api.handleListResult
import com.example.domain_api.mapper.IMenuProductMapper
import com.example.domain_api.model.entity.product_with_category.MenuProductCategoryReference
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuProductRepository @Inject constructor(
    private val apiRepository: ApiRepo,
    private val menuProductDao: MenuProductDao,
    private val menuProductMapper: IMenuProductMapper
) : MenuProductRepo {

    override suspend fun refreshMenuProductList() {
        apiRepository.getMenuProductList()
            .handleListResult(MENU_PRODUCT_TAG) { menuProductServerList ->
                if (menuProductServerList != null) {
                    val menuProductWithCategoryList =
                        menuProductServerList.map(menuProductMapper::toEntityModel)
                    val menuProductList =
                        menuProductWithCategoryList.map { menuProductWithCategory ->
                            menuProductWithCategory.menuProduct
                        }
                    menuProductDao.insertAll(menuProductList)

                    menuProductWithCategoryList.forEach { menuProductWithCategory ->
                        menuProductDao.deleteCategoryReferenceByMenuProductUuid(
                            menuProductWithCategory.menuProduct.uuid
                        )
                        val categoryReferenceList =
                            menuProductWithCategory.categoryList.map { category ->
                                MenuProductCategoryReference(
                                    menuProductUuid = menuProductWithCategory.menuProduct.uuid,
                                    categoryUuid = category.uuid,
                                )
                            }
                        menuProductDao.insertAllCategoryReference(categoryReferenceList)
                    }
                }
            }
    }

    override fun observeMenuProductList(): Flow<List<MenuProduct>> {
        return menuProductDao.observeMenuProductList().mapListFlow(menuProductMapper::toModel)
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductDao.observeMenuProductByUuid(menuProductUuid)
            .mapFlow(menuProductMapper::toModel)
    }

    override suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct? {
        val menuProduct = menuProductDao.getMenuProductByUuid(menuProductUuid)
        return menuProduct?.let {
            menuProductMapper.toModel(menuProduct)
        }
    }
}