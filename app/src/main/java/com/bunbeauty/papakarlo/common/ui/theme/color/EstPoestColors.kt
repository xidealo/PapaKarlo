package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red350
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red50

val EstPoestLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Red350,
                surfaceVariant = Red50,
                strokeVariant = Red100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val EstPoestDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Red350,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
