package com.bunbeauty.papakarlo.data.local.db.menu_product

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuProductRepository @Inject constructor(private val menuProductDao: MenuProductDao) :
    MenuProductRepo {

    override suspend fun insert(menuProduct: MenuProduct) {
        menuProductDao.insert(menuProduct)
    }

    override fun getMenuProductList(productCode: ProductCode): LiveData<List<MenuProduct>> {
        return if (productCode == ProductCode.ALL) {
            menuProductDao.getMenuProductListLiveData()
        } else {
            menuProductDao.getMenuProductListByCodeLiveData(productCode)
        }
    }
}