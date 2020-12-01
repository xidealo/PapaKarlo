package com.bunbeauty.papakarlo.data.local.converter

import androidx.room.TypeConverter
import com.bunbeauty.papakarlo.enums.ProductCode

class ProductCodeConverter {
    @TypeConverter
    fun convertFromProductCode(productCode: ProductCode): String {
        return productCode.name
    }

    @TypeConverter
    fun convertToProductCode(name: String): ProductCode {
        return ProductCode.valueOf(name)
    }
}