package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Brown500
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red50

val VkusKavkazaLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Brown500,
                surfaceVariant = Red50,
                strokeVariant = Red100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val VkusKavkazaDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Brown500,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
