package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange400
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red50

val AntalyaKebabLightColors = AppColors(
    mainColors = ColorDefaults.lightMainColors(
        primary = Orange400,
        surfaceVariant = Red50,
        strokeVariant = Red100
    ),
    orderColors = ColorDefaults.orderColors(),
    statusColors = ColorDefaults.statusColors(),
    bunBeautyBrandColor = LightBlue,
    isLight = true
)

val AntalyaKebabDarkColors = AppColors(
    mainColors = ColorDefaults.darkMainColors(
        primary = Orange400
    ),
    orderColors = ColorDefaults.orderColors(),
    statusColors = ColorDefaults.statusColors(),
    bunBeautyBrandColor = LightBlue,
    isLight = false
)
