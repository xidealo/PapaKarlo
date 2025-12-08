package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red250
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red50

val GustoPubLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Red250,
                surfaceVariant = Red50,
                strokeVariant = Red100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val GustoPubDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Red250,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
