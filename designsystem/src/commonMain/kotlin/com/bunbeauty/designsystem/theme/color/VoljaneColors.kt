package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Gold100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Orange350
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Orange50

val VoljaneColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Orange350,
                surfaceVariant = Orange50,
                strokeVariant = Gold100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val VoljaneDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Orange350,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
