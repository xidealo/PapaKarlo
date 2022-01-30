package com.bunbeauty.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<CategoryEntity> {

    @Query(
        """SELECT * FROM CategoryEntity
        WHERE (SELECT COUNT(*) FROM MenuProductCategoryReference
            WHERE CategoryEntity.uuid = MenuProductCategoryReference.categoryUuid) > 0"""
    )
    fun observeCategoryList(): Flow<List<CategoryEntity>>
}