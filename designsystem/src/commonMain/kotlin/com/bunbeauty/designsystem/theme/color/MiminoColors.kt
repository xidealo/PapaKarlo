package com.bunbeauty.designsystem.theme.color

import androidx.compose.ui.graphics.Color
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red50

private val MiminoPrimary = Color(0xFF88251F)

val MiminoLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = MiminoPrimary,
                surfaceVariant = Red50,
                strokeVariant = Red100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val MiminoDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = MiminoPrimary,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
