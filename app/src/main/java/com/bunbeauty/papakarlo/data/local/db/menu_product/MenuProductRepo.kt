package com.bunbeauty.papakarlo.data.local.db.menu_product

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode

interface MenuProductRepo {

    suspend fun insert(menuProduct: MenuProduct)

    fun getMenuProductList(productCode: ProductCode): LiveData<List<MenuProduct>>
}