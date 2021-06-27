package com.bunbeauty.data

import androidx.room.TypeConverter
import com.bunbeauty.domain.enums.ProductCode

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