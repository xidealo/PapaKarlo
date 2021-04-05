package com.bunbeauty.domain.repository.menu_product

import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.repository.api.IApiRepository
import kotlinx.coroutines.flow.*
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

    override fun getMenuProductList(): Flow<List<MenuProduct>> {
        return menuProductDao.getMenuProductListFlow()
    }
}