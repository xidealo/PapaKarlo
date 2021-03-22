package com.bunbeauty.domain.repository.menu_product

import androidx.lifecycle.LiveData
import com.bunbeauty.data.enums.ProductCode

interface MenuProductRepo {

    suspend fun insert(menuProduct: com.bunbeauty.data.model.MenuProduct)

    suspend fun getMenuProductRequest()
    fun getMenuProductList(productCode: ProductCode): LiveData<List<com.bunbeauty.data.model.MenuProduct>>
}