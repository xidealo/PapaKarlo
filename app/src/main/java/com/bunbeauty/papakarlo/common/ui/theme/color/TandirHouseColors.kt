package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange400
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Orange50

val TandirHouseLightColors =
    AppColors(
        mainColors =
            ColorDefaults.lightMainColors(
                primary = Orange400,
                surfaceVariant = Orange50,
                strokeVariant = Orange100,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = true,
    )

val TandirHouseDarkColors =
    AppColors(
        mainColors =
            ColorDefaults.darkMainColors(
                primary = Orange400,
            ),
        orderColors = ColorDefaults.orderColors(),
        statusColors = ColorDefaults.statusColors(),
        bunBeautyBrandColor = LightBlue,
        isLight = false,
    )
