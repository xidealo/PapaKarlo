package com.bunbeauty.designsystem.theme.color

import androidx.compose.ui.graphics.Color
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightBlue

private val EmojiCenterPrimary = Color(0xFF5A6EAB)
private val EmojiCenterSurfaceVariant = Color(0xFFE8E8F5)
private val EmojiCenterStrokeVariant = Color(0xFFD0D0E8)

val EmojiCenterLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = EmojiCenterPrimary,
                surfaceVariant = EmojiCenterSurfaceVariant,
                strokeVariant = EmojiCenterStrokeVariant,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val EmojiCenterDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = EmojiCenterPrimary,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
