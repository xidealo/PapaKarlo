package com.bunbeauty.papakarlo.common.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val orange = Color(0xFFFF6900)
private val white = Color(0xFFFFFFFF)
private val black = Color(0xFF000000)
private val grey1 = Color(0xFFDDDDDD)
private val grey2 = Color(0xFFA7A5A5)
private val cream = Color(0xFFF2F1F6)
private val red = Color(0xFFB1021D)
private val purple = Color(0xFF815FB1)
private val blue = Color(0xFF5C82E0)
private val lightRed = Color(0xFFDD6962)
private val yellow = Color(0xFFECA441)
private val lightGreen = Color(0xFF86BD47)
private val green = Color(0xFF62BC71)
private val darkGrey = Color(0xFF7B7A80)
private val lightBlue = Color(0xFF0AB9E8)

val PapaKarloColors = AppColors(
    mainColors = MainColors(
        primary = orange,
        disabled = grey1,
        secondary = white,
        background = cream,
        surface = white,
        error = red,
        onPrimary = white,
        onDisabled = grey2,
        onSecondary = grey2,
        onBackground = black,
        onSurface = black,
        onSurfaceVariant = grey2,
        onError = white,
    ),
    orderColors = OrderColors(
        notAccepted = purple,
        accepted = blue,
        preparing = lightRed,
        sentOut = yellow,
        done = lightGreen,
        delivered = green,
        canceled = darkGrey,
        onOrder = white,
    ),
    statusColors = StatusColors(
        positive = green,
        warning = yellow,
        negative = lightRed,
        onStatus = white,
    ),
    bunBeautyBrandColor = lightBlue,
    isLight = true
)

val CheddarColors = AppColors(
    mainColors = MainColors(
        primary = orange,
        disabled = grey1,
        secondary = white,
        background = cream,
        surface = white,
        error = red,
        onPrimary = white,
        onDisabled = grey2,
        onSecondary = grey2,
        onBackground = black,
        onSurface = black,
        onSurfaceVariant = grey2,
        onError = white,
    ),
    orderColors = OrderColors(
        notAccepted = purple,
        accepted = blue,
        preparing = lightRed,
        sentOut = yellow,
        done = lightGreen,
        delivered = green,
        canceled = darkGrey,
        onOrder = white,
    ),
    statusColors = StatusColors(
        positive = green,
        warning = yellow,
        negative = lightRed,
        onStatus = white,
    ),
    bunBeautyBrandColor = lightBlue,
    isLight = false
)

val LocalAppColors = staticCompositionLocalOf { PapaKarloColors }

@Stable
class AppColors(
    mainColors: MainColors,
    orderColors: OrderColors,
    statusColors: StatusColors,
    bunBeautyBrandColor: Color,
    isLight: Boolean
) {
    var mainColors by mutableStateOf(mainColors)
        private set
    var orderColors by mutableStateOf(orderColors)
        private set
    var statusColors by mutableStateOf(statusColors)
        private set
    var bunBeautyBrandColor by mutableStateOf(bunBeautyBrandColor)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    fun copy(
        mainColors: MainColors = this.mainColors,
        orderColors: OrderColors = this.orderColors,
        statusColors: StatusColors = this.statusColors,
        bunBeautyBrandColor: Color = this.bunBeautyBrandColor,
        isLight: Boolean = this.isLight,
    ) = AppColors(
        mainColors = mainColors,
        orderColors = orderColors,
        statusColors = statusColors,
        bunBeautyBrandColor = bunBeautyBrandColor,
        isLight = isLight,
    )

    fun update(other: AppColors) {
        mainColors = other.mainColors
        orderColors = other.orderColors
        statusColors = other.statusColors
        bunBeautyBrandColor = other.bunBeautyBrandColor
        isLight = other.isLight
    }
}

@Stable
class MainColors(
    primary: Color,
    disabled: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onDisabled: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onSurfaceVariant: Color,
    onError: Color,
) {

    var primary by mutableStateOf(primary)
        private set
    var disabled by mutableStateOf(disabled)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var error by mutableStateOf(error)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onDisabled by mutableStateOf(onDisabled)
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

    fun copy(
        primary: Color = this.primary,
        disabled: Color = this.disabled,
        secondary: Color = this.secondary,
        background: Color = this.background,
        surface: Color = this.surface,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        onDisabled: Color = this.onDisabled,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        onSurface: Color = this.onSurface,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        onError: Color = this.onError,
    ) = MainColors(
        primary = primary,
        disabled = disabled,
        secondary = secondary,
        background = background,
        surface = surface,
        error = error,
        onPrimary = onPrimary,
        onDisabled = onDisabled,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
        onError = onError,
    )

    fun update(other: MainColors) {
        primary = other.primary
        disabled = other.disabled
        secondary = other.secondary
        background = other.background
        surface = other.surface
        error = other.error
        onPrimary = other.onPrimary
        onDisabled = other.onDisabled
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        onSurfaceVariant = other.onSurfaceVariant
        onError = other.onError
    }
}

@Stable
class OrderColors(
    notAccepted: Color,
    accepted: Color,
    preparing: Color,
    sentOut: Color,
    done: Color,
    delivered: Color,
    canceled: Color,
    onOrder: Color,
) {
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
    var onOrder by mutableStateOf(onOrder)
        private set

    fun copy(
        notAccepted: Color = this.notAccepted,
        accepted: Color = this.accepted,
        preparing: Color = this.preparing,
        sentOut: Color = this.sentOut,
        done: Color = this.done,
        delivered: Color = this.delivered,
        canceled: Color = this.canceled,
        onOrder: Color = this.onOrder,
    ) = OrderColors(
        notAccepted = notAccepted,
        accepted = accepted,
        preparing = preparing,
        sentOut = sentOut,
        done = done,
        delivered = delivered,
        canceled = canceled,
        onOrder = onOrder,
    )

    fun update(other: OrderColors) {
        notAccepted = other.notAccepted
        accepted = other.accepted
        preparing = other.preparing
        sentOut = other.sentOut
        done = other.done
        delivered = other.delivered
        canceled = other.canceled
        onOrder = other.onOrder
    }
}

@Stable
class StatusColors(
    positive: Color,
    warning: Color,
    negative: Color,
    onStatus: Color,
) {
    var positive by mutableStateOf(positive)
        private set
    var warning by mutableStateOf(warning)
        private set
    var negative by mutableStateOf(negative)
        private set
    var onStatus by mutableStateOf(onStatus)
        private set

    fun copy(
        positive: Color = this.positive,
        warning: Color = this.warning,
        negative: Color = this.negative,
        onStatus: Color = this.onStatus,
    ) = StatusColors(
        positive = positive,
        warning = warning,
        negative = negative,
        onStatus = onStatus
    )

    fun update(other: StatusColors) {
        positive = other.positive
        warning = other.warning
        negative = other.negative
        onStatus = other.onStatus
    }
}


