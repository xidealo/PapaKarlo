package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.MENU_PRODUCT_TAG
import com.bunbeauty.data.dao.category.ICategoryDao
import com.bunbeauty.data.dao.menu_product.IMenuProductDao
import com.bunbeauty.data.dao.menu_product_category_reference.IMenuProductCategoryReferenceDao
import com.bunbeauty.data.handleListResult
import com.bunbeauty.data.handleListResultAndReturn
import com.bunbeauty.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.data.network.model.MenuProductServer
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuProductRepository(
    private val apiRepository: ApiRepo,
    private val menuProductDao: IMenuProductDao,
    private val categoryDao: ICategoryDao,
    private val menuProductCategoryReferenceDao: IMenuProductCategoryReferenceDao,
    private val menuProductMapper: IMenuProductMapper
) : MenuProductRepo {

    private var menuProductListCache: List<MenuProduct>? = null

    override suspend fun getMenuProductList(): List<MenuProduct> {
        return menuProductListCache ?: apiRepository.getMenuProductList().handleListResultAndReturn(
            tag = MENU_PRODUCT_TAG,
            onError = {
                menuProductMapper.toMenuProductList(
                    menuProductDao.getMenuProductWithCategoryList()
                )
            },
            onSuccess = { menuProductServerList ->
                saveMenuLocally(menuProductServerList)
                menuProductServerList.map(menuProductMapper::toMenuProduct)
                    .also { menuProductList ->
                        menuProductListCache = menuProductList
                    }
            }
        )
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

    suspend fun saveMenuLocally(menuProductServerList: List<MenuProductServer>) {
        menuProductMapper.toCategoryEntityList(menuProductServerList)
            .let { categoryEntityList ->
                categoryDao.insertCategoryList(categoryEntityList)
            }
        menuProductServerList.map(menuProductMapper::toMenuProductEntity)
            .let { menuProductEntityList ->
                menuProductDao.insertMenuProductList(menuProductEntityList)
            }
        menuProductServerList.flatMap(menuProductMapper::toMenuProductCategoryReference)
            .let { menuProductCategoryReferenceList ->
                menuProductCategoryReferenceDao.updateMenuProductReferenceList(
                    menuProductCategoryReferenceList
                )
            }
    }
}