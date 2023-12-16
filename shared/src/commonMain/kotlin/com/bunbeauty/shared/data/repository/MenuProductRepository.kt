package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.addition.IAdditionDao
import com.bunbeauty.shared.data.dao.category.ICategoryDao
import com.bunbeauty.shared.data.dao.menu_product.IMenuProductDao
import com.bunbeauty.shared.data.dao.menu_product_category_reference.IMenuProductCategoryReferenceDao
import com.bunbeauty.shared.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuProductRepository(
    private val networkConnector: NetworkConnector,
    private val menuProductDao: IMenuProductDao,
    private val categoryDao: ICategoryDao,
    private val menuProductCategoryReferenceDao: IMenuProductCategoryReferenceDao,
    private val menuProductMapper: IMenuProductMapper,
    private val additionDao: IAdditionDao,
) : CacheListRepository<MenuProduct>(), MenuProductRepo {

    override val tag: String = "MENU_PRODUCT_TAG"

    override suspend fun getMenuProductList(): List<MenuProduct> {
        return getCacheOrListData(
            onApiRequest = networkConnector::getMenuProductList,
            onLocalRequest = {
                menuProductMapper.toMenuProductList(
                    menuProductDao.getMenuProductWithCategoryList()
                )
            },
            onSaveLocally = ::saveMenuLocally,
            serverToDomainModel = menuProductMapper::toMenuProduct
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

    override suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct? {
        return getCacheOrListData(
            onApiRequest = networkConnector::getMenuProductList,
            onLocalRequest = {
                menuProductMapper.toMenuProductList(
                    menuProductDao.getMenuProductWithCategoryList()
                )
            },
            onSaveLocally = ::saveMenuLocally,
            serverToDomainModel = menuProductMapper::toMenuProduct
        ).find { menuProduct -> menuProduct.uuid == menuProductUuid }
    }

    private suspend fun saveMenuLocally(menuProductServerList: List<MenuProductServer>) {
        menuProductMapper.toCategoryEntityList(menuProductServerList)
            .let { categoryEntityList ->
                categoryDao.insertCategoryList(categoryEntityList)
            }
        menuProductServerList.map(menuProductMapper::toMenuProductEntity)
            .let { menuProductEntityList ->
                menuProductDao.insertMenuProductList(menuProductEntityList)
            }

        menuProductMapper.toAdditionEntityList(menuProductServerList)
            .let { additionEntityList ->
                additionDao.insertList(additionEntityList)
            }

        menuProductServerList.flatMap(menuProductMapper::toMenuProductCategoryReference)
            .let { menuProductCategoryReferenceList ->
                menuProductCategoryReferenceDao.updateMenuProductReferenceList(
                    menuProductCategoryReferenceList
                )
            }
    }
}