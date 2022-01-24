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
import com.example.domain_api.model.entity.product_with_category.MenuProductWithCategory
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

                    compareMenuProductCategoryReferenceWithLocal(menuProductWithCategoryList)
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

    suspend fun compareMenuProductCategoryReferenceWithLocal(menuProductWithCategoryList: List<MenuProductWithCategory>) {
        menuProductWithCategoryList.forEach { menuProductWithCategory ->
            val localMenuProductWithCategory =
                menuProductDao.getMenuProductWithCategoryByUuid(menuProductWithCategory.menuProduct.uuid)
            if (localMenuProductWithCategory != null) {
                localMenuProductWithCategory.categoryList.filter { localCategoryEntity ->
                    menuProductWithCategory.categoryList.none { categoryEntity ->
                        categoryEntity.uuid == localCategoryEntity.uuid
                    }
                }.onEach { outdatedCategory ->
                    val categoryReference = MenuProductCategoryReference(
                        menuProductUuid = menuProductWithCategory.menuProduct.uuid,
                        categoryUuid = outdatedCategory.uuid
                    )
                    menuProductDao.deleteCategoryReference(categoryReference)
                }
                val menuProductCategoryReferenceList =
                    menuProductWithCategory.categoryList.filter { categoryEntity ->
                        localMenuProductWithCategory.categoryList.none { localCategoryEntity ->
                            categoryEntity.uuid == localCategoryEntity.uuid
                        }
                    }.map { newCategory ->
                        MenuProductCategoryReference(
                            menuProductUuid = menuProductWithCategory.menuProduct.uuid,
                            categoryUuid = newCategory.uuid
                        )
                    }
                menuProductDao.insertCategoryReferenceList(
                    menuProductCategoryReferenceList
                )
            }
        }
    }
}