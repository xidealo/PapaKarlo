package com.example.data_api.repository

import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.repo.MenuProductRepo
import com.example.data_api.dao.MenuProductDao
import com.example.domain_api.mapper.IMenuProductMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MenuProductRepository @Inject constructor(
    private val apiRepository: ApiRepo,
    private val menuProductDao: MenuProductDao,
    private val menuProductMapper: IMenuProductMapper
) : MenuProductRepo {

    override suspend fun refreshMenuProductList() {
        when (val result = apiRepository.getMenuProductList()) {
            is ApiResult.Success -> {
                if (result.data != null)
                    menuProductDao.insertAll(result.data!!.map(menuProductMapper::toEntityModel))
            }

            is ApiResult.Error -> {

            }
        }
    }

    override fun observeMenuProductList(): Flow<List<MenuProduct>> {
        return menuProductDao.observeMenuProductList().map { menuProductList ->
            menuProductList.map(menuProductMapper::toModel)
        }
    }

    override fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?> {
        return menuProductDao.observeMenuProductByUuid(menuProductUuid).map { menuProduct ->
            menuProduct?.let {
                menuProductMapper.toModel(menuProduct)
            }
        }
    }

    override suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct? {
        val menuProduct = menuProductDao.getMenuProductByUuid(menuProductUuid)
        return menuProduct?.let {
            menuProductMapper.toModel(menuProduct)
        }
    }
}