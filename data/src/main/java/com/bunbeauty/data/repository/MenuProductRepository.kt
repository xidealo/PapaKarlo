package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.MenuProductDao
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.model.ui.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuProductRepository @Inject constructor(
    private val menuProductDao: MenuProductDao,
    private val apiRepo: ApiRepo
) : MenuProductRepo {

    override suspend fun insert(menuProduct: MenuProduct) {
        menuProductDao.insert(menuProduct)
    }

    override suspend fun getMenuProductRequest() {
        menuProductDao.deleteAll()
        apiRepo.getMenuProductList().collect { menuProductList ->
            menuProductDao.insertAll(menuProductList)
        }
    }

    override fun getMenuProductListAsFlow(): Flow<List<MenuProduct>> {
        return menuProductDao.getMenuProductListFlow()
            .flowOn(IO)
            .map { menuProductList ->
                menuProductList.sortedBy { menuProduct ->
                    menuProduct.name
                }.filter { menuProduct ->
                    menuProduct.visible
                }
            }.flowOn(Default)
    }

    override fun getMenuProductAsFlow(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductDao.getMenuProductFlow(menuProductUuid)
    }

    override suspend fun getMenuProduct(menuProductUuid: String): MenuProduct? {
        return withContext(IO) {
            menuProductDao.getMenuProduct(menuProductUuid)
        }
    }

}