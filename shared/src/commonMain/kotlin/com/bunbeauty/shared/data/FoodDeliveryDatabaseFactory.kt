package com.bunbeauty.shared.data

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.db.CafeEntity
import com.bunbeauty.shared.db.CartProductAdditionEntity
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CategoryEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.MenuProductEntity
import com.bunbeauty.shared.db.OrderAdditionEntity
import com.bunbeauty.shared.db.OrderEntity
import com.bunbeauty.shared.db.OrderProductEntity
import com.bunbeauty.shared.db.UserAddressEntity

fun createFoodDeliveryDatabase(driver: SqlDriver): FoodDeliveryDatabase {
    val intAdapter = IntColumnAdapter
    return FoodDeliveryDatabase(
        driver = driver,
        additionEntityAdapter =
            AdditionEntity.Adapter(
                priceAdapter = intAdapter,
                priorityAdapter = intAdapter,
            ),
        additionGroupEntityAdapter =
            AdditionGroupEntity.Adapter(
                priorityAdapter = intAdapter,
            ),
        cafeEntityAdapter =
            CafeEntity.Adapter(
                fromTimeAdapter = intAdapter,
                toTimeAdapter = intAdapter,
                offsetAdapter = intAdapter,
            ),
        cartProductAdditionEntityAdapter =
            CartProductAdditionEntity.Adapter(
                priceAdapter = intAdapter,
                priorityAdapter = intAdapter,
            ),
        cartProductEntityAdapter =
            CartProductEntity.Adapter(
                countAdapter = intAdapter,
            ),
        categoryEntityAdapter =
            CategoryEntity.Adapter(
                priorityAdapter = intAdapter,
            ),
        menuProductEntityAdapter =
            MenuProductEntity.Adapter(
                newPriceAdapter = intAdapter,
                oldPriceAdapter = intAdapter,
                nutritionAdapter = intAdapter,
                barcodeAdapter = intAdapter,
            ),
        orderAdditionEntityAdapter =
            OrderAdditionEntity.Adapter(
                priorityAdapter = intAdapter,
            ),
        orderEntityAdapter =
            OrderEntity.Adapter(
                deliveryCostAdapter = intAdapter,
                oldTotalCostAdapter = intAdapter,
                newTotalCostAdapter = intAdapter,
                percentDiscountAdapter = intAdapter,
            ),
        orderProductEntityAdapter =
            OrderProductEntity.Adapter(
                countAdapter = intAdapter,
                newPriceAdapter = intAdapter,
                oldPriceAdapter = intAdapter,
                nutritionAdapter = intAdapter,
                barcodeAdapter = intAdapter,
                newCommonPriceAdapter = intAdapter,
                oldCommonPriceAdapter = intAdapter,
                newTotalCostAdapter = intAdapter,
                oldTotalCostAdapter = intAdapter,
            ),
        userAddressEntityAdapter =
            UserAddressEntity.Adapter(
                minOrderCostAdapter = intAdapter,
                normalDeliveryCostAdapter = intAdapter,
                forLowDeliveryCostAdapter = intAdapter,
                lowDeliveryCostAdapter = intAdapter,
            ),
    )
}
