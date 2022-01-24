package com.example.domain_api.model.entity.product_with_category

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.domain_api.model.entity.CategoryEntity
import com.example.domain_api.model.entity.product.MenuProductEntity

class MenuProductWithCategory(

    @Embedded
    val menuProduct: MenuProductEntity,

    @Relation(
        parentColumn = "uuid",
        entity = CategoryEntity::class,
        entityColumn = "uuid",
        associateBy = Junction(
            value = MenuProductCategoryReference::class,
            parentColumn = "menuProductUuid",
            entityColumn = "categoryUuid"
        )
    )
    val categoryList: List<CategoryEntity>
)