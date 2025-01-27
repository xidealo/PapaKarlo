package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red250
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red50

val GustoPubLightColors = AppColors(
    mainColors = ColorDefaults.lightMainColors(
        primary = Red250,
        surfaceVariant = Red50,
        strokeVariant = Red100
    ),
    orderColors = ColorDefaults.orderColors(),
    statusColors = ColorDefaults.statusColors(),
    bunBeautyBrandColor = LightBlue,
    isLight = true
)

val GustoPubDarkColors = AppColors(
    mainColors = ColorDefaults.darkMainColors(
        primary = Red250
    ),
    orderColors = ColorDefaults.orderColors(),
    statusColors = ColorDefaults.statusColors(),
    bunBeautyBrandColor = LightBlue,
    isLight = false
)
