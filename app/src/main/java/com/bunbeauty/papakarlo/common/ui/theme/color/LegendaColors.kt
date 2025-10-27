package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red400
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red50

val LegendaLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Red400,
                surfaceVariant = Red50,
                strokeVariant = Red100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val LegendaDarkColors =
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
