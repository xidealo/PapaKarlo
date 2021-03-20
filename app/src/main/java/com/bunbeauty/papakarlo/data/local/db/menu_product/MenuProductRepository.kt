package com.bunbeauty.papakarlo.data.local.db.menu_product

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import kotlinx.coroutines.flow.collect
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
            for (menuProduct in menuProductList) {
                insert(menuProduct)
            }
        }
    }

    override fun getMenuProductList(productCode: ProductCode): LiveData<List<MenuProduct>> {
        return if (productCode == ProductCode.ALL) {
            menuProductDao.getMenuProductListLiveData()
        } else {
            menuProductDao.getMenuProductListByCodeLiveData(productCode.name)
        }
    }
}