package com.bunbeauty.designsystem.theme.color

import androidx.compose.ui.graphics.Color
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Black100
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Black200
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Black300
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Black50
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Blue
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Cream
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.DarkGrey
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Green
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Grey1
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Grey2
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Grey3
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.LightGreen
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Purple
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red200
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Red500
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.White
import com.bunbeauty.designsystem.theme.color.FoodDeliveryColors.Yellow

object ColorDefaults {
    fun lightMainColors(
        primary: Color,
        surfaceVariant: Color,
        strokeVariant: Color,
    ): MainColors =
        MainColors(
            primary = primary,
            disabled = Grey1,
            secondary = White,
            background = Cream,
            surface = White,
            surfaceVariant = surfaceVariant,
            error = Red500,
            onPrimary = White,
            onDisabled = Grey3,
            onSecondary = Grey3,
            onBackground = Black300,
            onSurface = Black300,
            onSurfaceVariant = Grey2,
            onError = White,
            stroke = Cream,
            strokeVariant = strokeVariant,
        )

    fun darkMainColors(
        primary: Color,
        surface: Color = Black200,
    ): MainColors =
        MainColors(
            primary = primary,
            disabled = Black100,
            secondary = Black200,
            background = Black300,
            surface = surface,
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
        )

    fun orderColors(): OrderColors =
        OrderColors(
            notAccepted = Purple,
            accepted = Blue,
            preparing = Red200,
            sentOut = Yellow,
            done = LightGreen,
            delivered = Green,
            canceled = DarkGrey,
            onOrder = White,
        )

    fun statusColors(): StatusColors =
        StatusColors(
            positive = Green,
            warning = Yellow,
            negative = Red200,
            onStatus = White,
        )
}
