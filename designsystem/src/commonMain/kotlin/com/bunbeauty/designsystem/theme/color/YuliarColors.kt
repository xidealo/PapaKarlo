package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Gold100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Gold200
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Orange50

val YuliarLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Gold200,
                surfaceVariant = Orange50,
                strokeVariant = Gold100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val YuliarDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Gold200,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
