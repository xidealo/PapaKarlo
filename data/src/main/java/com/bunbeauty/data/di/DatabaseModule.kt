package com.bunbeauty.data.di

import com.bunbeauty.data.FoodDeliveryDatabase
import com.bunbeauty.data.sql_delight.dao.cafe.CafeDao
import com.bunbeauty.data.sql_delight.dao.cafe.ICafeDao
import com.bunbeauty.data.sql_delight.dao.cart_product.CartProductDao
import com.bunbeauty.data.sql_delight.dao.cart_product.ICartProductDao
import com.bunbeauty.data.sql_delight.dao.category.CategoryDao
import com.bunbeauty.data.sql_delight.dao.category.ICategoryDao
import com.bunbeauty.data.sql_delight.dao.city.CityDao
import com.bunbeauty.data.sql_delight.dao.city.ICityDao
import com.bunbeauty.data.sql_delight.dao.menu_product.IMenuProductDao
import com.bunbeauty.data.sql_delight.dao.menu_product.MenuProductDao
import com.bunbeauty.data.sql_delight.dao.menu_product_category_reference.IMenuProductCategoryReferenceDao
import com.bunbeauty.data.sql_delight.dao.menu_product_category_reference.MenuProductCategoryReferenceDao
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun databaseModule() = module {
    single {
        val driver =
            AndroidSqliteDriver(FoodDeliveryDatabase.Schema, androidContext(), "foodDelivery.db")
        FoodDeliveryDatabase(driver)
    }
    single<ICafeDao> {
        CafeDao(foodDeliveryDatabase = get())
    }
    single<ICartProductDao> {
        CartProductDao(foodDeliveryDatabase = get())
    }
    single<ICityDao> {
        CityDao(foodDeliveryDatabase = get())
    }
    single<IMenuProductDao> {
        MenuProductDao(foodDeliveryDatabase = get())
    }
    single<ICategoryDao> {
        CategoryDao(foodDeliveryDatabase = get())
    }
    single<IMenuProductCategoryReferenceDao> {
        MenuProductCategoryReferenceDao(foodDeliveryDatabase = get())
    }
}