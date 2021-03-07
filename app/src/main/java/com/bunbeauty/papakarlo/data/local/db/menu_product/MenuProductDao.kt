package com.bunbeauty.papakarlo.data.local.db.menu_product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode

@Dao
interface MenuProductDao: BaseDao<MenuProduct> {

    @Query("SELECT * FROM MenuProduct ORDER BY productCode, cost")
    fun getMenuProductListLiveData(): LiveData<List<MenuProduct>>

    @Query("SELECT * FROM MenuProduct WHERE productCode = :productCode ORDER BY cost")
    fun getMenuProductListByCodeLiveData(productCode: String): LiveData<List<MenuProduct>>
}