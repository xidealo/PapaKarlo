package com.bunbeauty.shared.ui.theme.color

import com.bunbeauty.shared.ui.theme.color.FoodDeliveryColors.Green100
import com.bunbeauty.shared.ui.theme.color.FoodDeliveryColors.Green50
import com.bunbeauty.shared.ui.theme.color.FoodDeliveryColors.Green500
import com.bunbeauty.shared.ui.theme.color.FoodDeliveryColors.LightBlue

val UsadbaLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Green500,
                surfaceVariant = Green50,
                strokeVariant = Green100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val UsadbaDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Green500,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
