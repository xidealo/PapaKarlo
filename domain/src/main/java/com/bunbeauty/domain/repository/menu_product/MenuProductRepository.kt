package com.bunbeauty.domain.repository.menu_product

import com.bunbeauty.data.dao.MenuProductDao
import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.model.MenuProduct
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MenuProductRepository @Inject constructor(
    private val menuProductDao: MenuProductDao,
    private val apiRepository: IApiRepository
) : MenuProductRepo {

    override suspend fun insert(menuProduct: MenuProduct) {
        menuProductDao.insert(menuProduct)
    }

    override suspend fun getMenuProductRequest() {
        menuProductDao.deleteAll()
        apiRepository.getMenuProductList().collect { menuProductList ->
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
}