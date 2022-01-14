package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.CategoryEntity
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