package com.bunbeauty.shared.di

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.DataStoreRepository
import com.bunbeauty.shared.data.DatabaseDriverFactory
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.db.CafeEntity
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CategoryEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderProductEntity
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = DatabaseDriverFactory(context = get()).createDriver()
        FoodDeliveryDatabase(
            driver = driver,
            cafeEntityAdapter = CafeEntity.Adapter(
                fromTimeAdapter = IntColumnAdapter,
                toTimeAdapter = IntColumnAdapter,
                offsetAdapter = IntColumnAdapter,
            ),
            cartProductEntityAdapter = CartProductEntity.Adapter(
                countAdapter = IntColumnAdapter,
            ),
            categoryEntityAdapter = CategoryEntity.Adapter(
                priorityAdapter = IntColumnAdapter,
            ),
            menuProductEntityAdapter = MenuProductEntity.Adapter(
                newPriceAdapter = IntColumnAdapter,
                oldPriceAdapter = IntColumnAdapter,
                nutritionAdapter = IntColumnAdapter,
                barcodeAdapter = IntColumnAdapter,
            ),
            orderEntityAdapter = OrderEntity.Adapter(
                deliveryCostAdapter = IntColumnAdapter,
                oldTotalCostAdapter = IntColumnAdapter,
                newTotalCostAdapter = IntColumnAdapter,
                percentDiscountAdapter = IntColumnAdapter,
            ),
            orderProductEntityAdapter = OrderProductEntity.Adapter(
                countAdapter = IntColumnAdapter,
                newPriceAdapter = IntColumnAdapter,
                oldPriceAdapter = IntColumnAdapter,
                nutritionAdapter = IntColumnAdapter,
                barcodeAdapter = IntColumnAdapter,
            ),
        )
    }
    single<DataStoreRepo> {
        DataStoreRepository()
    }
    single {
        UuidGenerator()
    }
}