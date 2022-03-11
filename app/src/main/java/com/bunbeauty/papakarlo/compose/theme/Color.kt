package com.bunbeauty.papakarlo.compose.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeStatus

val orange = Color(0xFFFF6900)
val white = Color(0xFFFFFFFF)
val black = Color(0xFF000000)
val grey = Color(0xFFBABABA)
val lightGrey = Color(0xFFD6D6D6)
val cream = Color(0xFFF2F1F6)
val red = Color(0xFFB1021D)
val purple = Color(0xFF815FB1)
val blue = Color(0xFF5C82E0)
val lightRed = Color(0xFFDD6962)
val yellow = Color(0xFFECA441)
val lightGreen = Color(0xFF86BD47)
val green = Color(0xFF62BC71)
val darkGrey = Color(0xFF7B7A80)
val lightBlue = Color(0xFF0AB9E8)

val LightColors = AppColors(
    primary = orange,
    secondary = white,
    background = cream,
    surface = white,
    surfaceVariant = lightGrey,
    error = red,
    notAccepted = purple,
    accepted = blue,
    preparing = lightRed,
    sentOut = yellow,
    done = lightGreen,
    delivered = green,
    canceled = darkGrey,
    bunBeautyBrandColor = lightBlue,
    open = green,
    closeSoon = yellow,
    closed = lightRed,
    onPrimary = white,
    onSecondary = grey,
    onBackground = black,
    onSurface = black,
    onSurfaceVariant = grey,
    onError = white,
    onStatus = white,
    isLight = true
)

val DarkColors = AppColors(
    primary = orange,
    secondary = white,
    background = cream,
    surface = white,
    surfaceVariant = lightGrey,
    error = red,
    notAccepted = purple,
    accepted = blue,
    preparing = lightRed,
    sentOut = yellow,
    done = lightGreen,
    delivered = green,
    canceled = darkGrey,
    bunBeautyBrandColor = lightBlue,
    open = green,
    closeSoon = yellow,
    closed = lightRed,
    onPrimary = white,
    onSecondary = grey,
    onBackground = black,
    onSurface = black,
    onSurfaceVariant = grey,
    onError = white,
    onStatus = white,
    isLight = false
)

val LocalAppColors = staticCompositionLocalOf { LightColors }

@Stable
class AppColors(
    primary: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    surfaceVariant: Color,
    error: Color,
    notAccepted: Color,
    accepted: Color,
    preparing: Color,
    sentOut: Color,
    done: Color,
    delivered: Color,
    canceled: Color,
    bunBeautyBrandColor: Color,
    open: Color,
    closeSoon: Color,
    closed: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onSurfaceVariant: Color,
    onError: Color,
    onStatus: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var surfaceVariant by mutableStateOf(surfaceVariant)
        private set
    var error by mutableStateOf(error)
        private set
    var notAccepted by mutableStateOf(notAccepted)
        private set
    var accepted by mutableStateOf(accepted)
        private set
    var preparing by mutableStateOf(preparing)
        private set
    var sentOut by mutableStateOf(sentOut)
        private set
    var done by mutableStateOf(done)
        private set
    var delivered by mutableStateOf(delivered)
        private set
    var canceled by mutableStateOf(canceled)
        private set
    var bunBeautyBrandColor by mutableStateOf(bunBeautyBrandColor)
        private set
    var open by mutableStateOf(open)
        private set
    var closeSoon by mutableStateOf(closeSoon)
        private set
    var closed by mutableStateOf(closed)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant)
        private set
    var onError by mutableStateOf(onError)
        private set
    var onStatus by mutableStateOf(onStatus)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    val textSelectionColors = TextSelectionColors(
        handleColor = primary,
        backgroundColor = primary.copy(alpha = 0.4f)
    )

    val surfaceGradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, surface)
    )

    @Composable
    fun mainButtonColors(): ButtonColors {
        return ButtonDefaults.buttonColors(
            backgroundColor = primary,
            contentColor = onPrimary,
            disabledBackgroundColor = primary.copy(alpha = 0.8f),
            disabledContentColor = onPrimary.copy(alpha = 0.8f)
        )
    }

    @Composable
    fun secondaryButtonColors(): ButtonColors {
        return ButtonDefaults.buttonColors(
            backgroundColor = secondary,
            contentColor = onSecondary,
            disabledBackgroundColor = secondary.copy(alpha = 0.8f),
            disabledContentColor = onSecondary.copy(alpha = 0.8f)
        )
    }

    @Composable
    fun textFieldColors(): TextFieldColors {
        return TextFieldDefaults.textFieldColors(
            textColor = onSurface,
            disabledTextColor = onSurfaceVariant,
            backgroundColor = surface,
            cursorColor = primary,
            errorCursorColor = error,
            focusedIndicatorColor = primary,
            unfocusedIndicatorColor = onSurfaceVariant,
            disabledIndicatorColor = onSurfaceVariant,
            errorIndicatorColor = error,
            leadingIconColor = onSurfaceVariant,
            disabledLeadingIconColor = onSurfaceVariant,
            errorLeadingIconColor = error,
            trailingIconColor = onSurfaceVariant,
            disabledTrailingIconColor = onSurfaceVariant,
            errorTrailingIconColor = error,
            focusedLabelColor = primary,
            unfocusedLabelColor = onSurfaceVariant,
            disabledLabelColor = onSurfaceVariant,
            errorLabelColor = error,
            placeholderColor = onSurfaceVariant,
            disabledPlaceholderColor = onSurfaceVariant
        )
    }

    fun orderColor(orderStatus: OrderStatus): Color {
        return when (orderStatus) {
            OrderStatus.NOT_ACCEPTED -> notAccepted
            OrderStatus.ACCEPTED -> accepted
            OrderStatus.PREPARING -> preparing
            OrderStatus.SENT_OUT -> sentOut
            OrderStatus.DONE -> done
            OrderStatus.DELIVERED -> delivered
            OrderStatus.CANCELED -> canceled
        }
    }

    fun cafeStatusColor(cafeStatus: CafeStatus): Color {
        return when (cafeStatus) {
            CafeStatus.OPEN -> open
            CafeStatus.CLOSE_SOON -> closeSoon
            CafeStatus.CLOSED -> closed
        }
    }

    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        surface: Color = this.surface,
        surfaceVariant: Color = this.surfaceVariant,
        error: Color = this.error,
        notAccepted: Color = this.notAccepted,
        accepted: Color = this.accepted,
        preparing: Color = this.preparing,
        sentOut: Color = this.sentOut,
        done: Color = this.done,
        delivered: Color = this.delivered,
        canceled: Color = this.canceled,
        bunBeautyBrandColor: Color = this.bunBeautyBrandColor,
        open: Color = this.open,
        closeSoon: Color = this.closeSoon,
        closed: Color = this.closed,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        onSurface: Color = this.onSurface,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        onError: Color = this.onError,
        onStatus: Color = this.onStatus,
        isLight: Boolean = this.isLight,
    ) = AppColors(
        primary = primary,
        secondary = secondary,
        background = background,
        surface = surface,
        surfaceVariant = surfaceVariant,
        error = error,
        notAccepted = notAccepted,
        accepted = accepted,
        preparing = preparing,
        sentOut = sentOut,
        done = done,
        delivered = delivered,
        canceled = canceled,
        bunBeautyBrandColor = bunBeautyBrandColor,
        open = open,
        closeSoon = closeSoon,
        closed = closed,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
        onError = onError,
        onStatus = onStatus,
        isLight = isLight,
    )

    fun update(other: AppColors) {
        primary = other.primary
        secondary = other.secondary
        background = other.background
        surface = other.surface
        surfaceVariant = other.surfaceVariant
        error = other.error
        notAccepted = other.notAccepted
        accepted = other.accepted
        preparing = other.preparing
        sentOut = other.sentOut
        done = other.done
        delivered = other.delivered
        canceled = other.canceled
        bunBeautyBrandColor = other.bunBeautyBrandColor
        open = other.open
        closeSoon = other.closeSoon
        closed = other.closed
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        onSurfaceVariant = other.onSurfaceVariant
        onError = other.onError
        onStatus = other.onStatus
        isLight = other.isLight
    }
}