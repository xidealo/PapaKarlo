package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red400
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red50

val DjanLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Red400,
                surfaceVariant = Red50,
                strokeVariant = Red100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val DjanDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Red400,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
