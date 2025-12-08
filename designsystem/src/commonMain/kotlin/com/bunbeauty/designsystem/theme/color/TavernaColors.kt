package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Gold100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Orange50
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red400
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red600

val TavernaColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Red600,
                surfaceVariant = Orange50,
                strokeVariant = Gold100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val TavernaDarkColors =
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
