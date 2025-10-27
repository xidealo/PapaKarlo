package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange300
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange50

val PapaKarloLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Orange300,
                surfaceVariant = Orange50,
                strokeVariant = Orange100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val PapaKarloDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Orange300,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
