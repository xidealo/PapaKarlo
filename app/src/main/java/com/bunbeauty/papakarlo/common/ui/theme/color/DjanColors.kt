package com.bunbeauty.papakarlo.common.ui.theme.color

import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black300
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black200
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Black50
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Blue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Cream
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.DarkGrey
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Green
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Grey1
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Grey2
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Grey3
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightBlue
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.LightGreen
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red200
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Purple
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red500
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red400
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red100
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Red50
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.White
import com.bunbeauty.papakarlo.common.ui.theme.color.FoodDeliveryColors.Yellow

val DjanLightColors = AppColors(
    mainColors = MainColors(
        primary = Red400,
        disabled = Grey1,
        secondary = White,
        background = Cream,
        surface = White,
        surfaceVariant = Red100,
        error = Red500,
        onPrimary = White,
        onDisabled = Grey3,
        onSecondary = Grey3,
        onBackground = Black300,
        onSurface = Black300,
        onSurfaceVariant = Grey2,
        onError = White,
        stroke = Cream,
        strokeVariant = Red50,
    ),
    orderColors = OrderColors(
        notAccepted = Purple,
        accepted = Blue,
        preparing = Red200,
        sentOut = Yellow,
        done = LightGreen,
        delivered = Green,
        canceled = DarkGrey,
        onOrder = White
    ),
    statusColors = StatusColors(
        positive = Green,
        warning = Yellow,
        negative = Red200,
        info = Red400,
        onStatus = White
    ),
    bunBeautyBrandColor = LightBlue,
    isLight = true
)

val DjanDarkColors = AppColors(
    mainColors = MainColors(
        primary = Red400,
        disabled = Black100,
        secondary = Black200,
        background = Black300,
        surface = Black200,
        surfaceVariant = Black100,
        error = Red500,
        onPrimary = White,
        onDisabled = Grey3,
        onSecondary = Grey3,
        onBackground = White,
        onSurface = White,
        onSurfaceVariant = Grey2,
        onError = White,
        stroke = Black50,
        strokeVariant = Black50,
    ),
    orderColors = OrderColors(
        notAccepted = Purple,
        accepted = Blue,
        preparing = Red200,
        sentOut = Yellow,
        done = LightGreen,
        delivered = Green,
        canceled = DarkGrey,
        onOrder = White
    ),
    statusColors = StatusColors(
        positive = Green,
        warning = Yellow,
        negative = Red200,
        info = Red400,
        onStatus = White
    ),
    bunBeautyBrandColor = LightBlue,
    isLight = false
)
