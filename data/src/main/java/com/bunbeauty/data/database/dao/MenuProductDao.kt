package com.bunbeauty.data.database.dao

import androidx.room.*
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.product.MenuProductEntity
import com.bunbeauty.data.database.entity.product_with_category.MenuProductCategoryReference
import com.bunbeauty.data.database.entity.product_with_category.MenuProductWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuProductDao: BaseDao<MenuProductEntity> {

    @Query("SELECT * FROM MenuProductEntity WHERE visible = 1")
    fun observeMenuProductList(): Flow<List<MenuProductWithCategory>>

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :uuid AND visible = 1")
    fun observeMenuProductByUuid(uuid: String): Flow<MenuProductEntity?>

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :uuid AND visible = 1")
    suspend fun getMenuProductByUuid(uuid: String): MenuProductEntity?

    @Query("SELECT * FROM MenuProductEntity WHERE uuid = :uuid AND visible = 1")
    suspend fun getMenuProductWithCategoryByUuid(uuid: String): MenuProductWithCategory?

    // MenuProductCategoryReference

    @Query("DELETE FROM MenuProductCategoryReference WHERE menuProductUuid = :menuProductUuid")
    suspend fun deleteCategoryReferenceByMenuProductUuid(menuProductUuid: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertCategoryReferenceList(categoryReferenceList: List<MenuProductCategoryReference>)

    @Delete
    suspend fun deleteCategoryReference(menuProductCategoryReference: MenuProductCategoryReference)
}