package com.bunbeauty.designsystem.theme.color

import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Gold100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Orange50
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Yellow500

val EmojiLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Yellow500,
                surfaceVariant = Orange50,
                strokeVariant = Gold100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val EmojiDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Yellow500,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
