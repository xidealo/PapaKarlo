package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.MenuProductDao
import com.bunbeauty.domain.mapper.IMenuProductMapper
import com.bunbeauty.domain.model.ui.product.MenuProduct
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MenuProductRepository @Inject constructor(
    private val menuProductDao: MenuProductDao,
    private val menuProductMapper: IMenuProductMapper,
    private val apiRepo: ApiRepo
) : MenuProductRepo {

    override suspend fun refreshMenuProducts() {
        val menuProductList = apiRepo.getMenuProductMap()
            .flowOn(IO)
            .map { menuProductMap ->
                menuProductMap.map { (uuid, menuProduct) ->
                    menuProductMapper.toEntityModel(uuid, menuProduct)
                }
            }.flowOn(Default)
            .first()
        menuProductList.forEach { menuProduct ->
            val localMenuProduct = menuProductDao.getMenuProductByUuid(menuProduct.uuid)
            if (localMenuProduct == null) {
                menuProductDao.insert(menuProduct)
            } else {
                menuProductDao.update(menuProduct)
            }
        }
    }

    override fun observeMenuProductList(): Flow<List<MenuProduct>> {
        return menuProductDao.observeMenuProductList()
            .flowOn(IO)
            .map { menuProductList ->
                menuProductList.filter { menuProduct ->
                    menuProduct.visible
                }.map { menuProduct ->
                    menuProductMapper.toUIModel(menuProduct)
                }.sortedBy { menuProduct ->
                    menuProduct.name
                }
            }.flowOn(Default)
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductDao.observeMenuProductByUuid(menuProductUuid).map { menuProduct ->
            menuProduct?.let {
                menuProductMapper.toUIModel(menuProduct)
            }
        }
    }

    override suspend fun getMenuProduct(menuProductUuid: String): MenuProduct? {
        return menuProductDao.getMenuProductByUuid(menuProductUuid)?.let { menuProduct ->
            menuProductMapper.toUIModel(menuProduct)
        }
    }

}