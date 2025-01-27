package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Gold100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Gold200
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange50

val YuliarLightColors = AppColors(
    mainColors = ColorDefaults.lightMainColors(
        primary = Gold200,
        surfaceVariant = Orange50,
        strokeVariant = Gold100
    ),
    orderColors = ColorDefaults.orderColors(),
    statusColors = ColorDefaults.statusColors(),
    bunBeautyBrandColor = LightBlue,
    isLight = true
)

val YuliarDarkColors = AppColors(
    mainColors = ColorDefaults.darkMainColors(
        primary = Gold200
    ),
    orderColors = ColorDefaults.orderColors(),
    statusColors = ColorDefaults.statusColors(),
    bunBeautyBrandColor = LightBlue,
    isLight = false
)
