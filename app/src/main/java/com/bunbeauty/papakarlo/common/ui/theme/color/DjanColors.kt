package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black1
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black2
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black3
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Blue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Cream
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.DarkGrey
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Green
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Grey1
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Grey2
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Grey3
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightGreen
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightRed
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Purple
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red1
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red2
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.White
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Yellow

val DjanLightColors = AppColors(
    mainColors = MainColors(
        primary = Red2,
        disabled = Grey1,
        secondary = White,
        background = Cream,
        surface = White,
        error = Red1,
        onPrimary = White,
        onDisabled = Grey3,
        onSecondary = Grey3,
        onBackground = Black1,
        onSurface = Black1,
        onSurfaceVariant = Grey2,
        onError = White
    ),
    orderColors = OrderColors(
        notAccepted = Purple,
        accepted = Blue,
        preparing = LightRed,
        sentOut = Yellow,
        done = LightGreen,
        delivered = Green,
        canceled = DarkGrey,
        onOrder = White
    ),
    statusColors = StatusColors(
        positive = Green,
        warning = Yellow,
        negative = LightRed,
        info = Red2,
        onStatus = White
    ),
    bunBeautyBrandColor = LightBlue,
    isLight = true
)

val DjanDarkColors = AppColors(
    mainColors = MainColors(
        primary = Red2,
        disabled = Black3,
        secondary = Black2,
        background = Black1,
        surface = Black2,
        error = Red1,
        onPrimary = White,
        onDisabled = Grey3,
        onSecondary = Grey3,
        onBackground = White,
        onSurface = White,
        onSurfaceVariant = Grey2,
        onError = White
    ),
    orderColors = OrderColors(
        notAccepted = Purple,
        accepted = Blue,
        preparing = LightRed,
        sentOut = Yellow,
        done = LightGreen,
        delivered = Green,
        canceled = DarkGrey,
        onOrder = White
    ),
    statusColors = StatusColors(
        positive = Green,
        warning = Yellow,
        negative = LightRed,
        info = Red2,
        onStatus = White
    ),
    bunBeautyBrandColor = LightBlue,
    isLight = false
)
