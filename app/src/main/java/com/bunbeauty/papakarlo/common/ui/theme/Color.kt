package com.bunbeauty.papakarlo.common.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.bunbeauty.shared.domain.model.cafe.CafeStatus
import com.bunbeauty.shared.domain.model.order.OrderStatus

val orange = Color(0xFFFF6900)
val white = Color(0xFFFFFFFF)
val black = Color(0xFF000000)
val grey = Color(0xFF989898)
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
    primaryDisabled = lightGrey,
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
    positive = green,
    warning = yellow,
    negative = lightRed,
    onPrimary = white,
    onPrimaryDisabled = grey,
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
    primaryDisabled = lightGrey,
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
    positive = green,
    warning = yellow,
    negative = lightRed,
    onPrimary = white,
    onPrimaryDisabled = grey,
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
    primaryDisabled: Color,
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
    positive: Color,
    warning: Color,
    negative: Color,
    onPrimary: Color,
    onPrimaryDisabled: Color,
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
    var primaryDisabled by mutableStateOf(primaryDisabled)
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
    var positive by mutableStateOf(positive)
        private set
    var warning by mutableStateOf(warning)
        private set
    var negative by mutableStateOf(negative)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onPrimaryDisabled by mutableStateOf(onPrimaryDisabled)
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

    val smsTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )

    val surfaceGradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, surface)
    )

    @Composable
    fun switcherButtonColors(): ButtonColors {
        return ButtonDefaults.buttonColors(
            containerColor = secondary,
            contentColor = onSecondary,
            disabledContainerColor = primary,
            disabledContentColor = onPrimary
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun textFieldColors(): TextFieldColors {
        return TextFieldDefaults.textFieldColors(
            textColor = onSurface,
            disabledTextColor = onSurfaceVariant,
            containerColor = surface,
            cursorColor = primary,
            errorCursorColor = error,
            focusedIndicatorColor = primary,
            unfocusedIndicatorColor = onSurfaceVariant,
            disabledIndicatorColor = onSurfaceVariant,
            errorIndicatorColor = error,
            focusedLeadingIconColor = onSurfaceVariant,
            disabledLeadingIconColor = onSurfaceVariant,
            errorLeadingIconColor = error,
            focusedTrailingIconColor = onSurfaceVariant,
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

    @Composable
    fun cardColors() = CardDefaults.cardColors(
        containerColor = FoodDeliveryTheme.colors.surface,
        disabledContainerColor = FoodDeliveryTheme.colors.surface,
    )

    @Composable
    fun secondaryCardColors() = CardDefaults.cardColors(
        containerColor = FoodDeliveryTheme.colors.secondary
    )

    @Composable
    fun mainButtonColors() = ButtonDefaults.buttonColors(
        containerColor = FoodDeliveryTheme.colors.primary,
        disabledContainerColor = FoodDeliveryTheme.colors.primaryDisabled,
        disabledContentColor = FoodDeliveryTheme.colors.onPrimaryDisabled,
        contentColor = FoodDeliveryTheme.colors.onPrimary
    )

    @Composable
    fun iconButtonColors() = IconButtonDefaults.iconButtonColors(
        containerColor = FoodDeliveryTheme.colors.primary,
        disabledContainerColor = FoodDeliveryTheme.colors.primaryDisabled,
        disabledContentColor = FoodDeliveryTheme.colors.onPrimaryDisabled,
        contentColor = FoodDeliveryTheme.colors.onPrimary
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun smsTextFieldColors(): TextFieldColors {
        return TextFieldDefaults.textFieldColors(
            textColor = onSurface,
            disabledTextColor = onSurfaceVariant,
            containerColor = surface,
            cursorColor = Color.Transparent,
            errorCursorColor = error,
            focusedIndicatorColor = primary,
            unfocusedIndicatorColor = onSurfaceVariant,
            disabledIndicatorColor = onSurfaceVariant,
            errorIndicatorColor = error,
            focusedLeadingIconColor = onSurfaceVariant,
            disabledLeadingIconColor = onSurfaceVariant,
            errorLeadingIconColor = error,
            focusedTrailingIconColor = onSurfaceVariant,
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
            CafeStatus.OPEN -> positive
            CafeStatus.CLOSE_SOON -> warning
            CafeStatus.CLOSED -> negative
        }
    }

    @Composable
    fun switcherButtonColor(enabled: Boolean): CardColors {
        return CardDefaults.cardColors(
            containerColor = if (enabled) {
                secondary
            } else {
                primary
            }
        )
    }

    fun switcherButtonTextColor(enabled: Boolean): Color {
        return if (enabled) {
            onSecondary
        } else {
            onPrimary
        }
    }

    fun copy(
        primary: Color = this.primary,
        primaryDisabled: Color = this.primaryDisabled,
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
        positive: Color = this.positive,
        warning: Color = this.warning,
        negative: Color = this.negative,
        onPrimary: Color = this.onPrimary,
        onPrimaryDisabled: Color = this.onPrimaryDisabled,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        onSurface: Color = this.onSurface,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        onError: Color = this.onError,
        onStatus: Color = this.onStatus,
        isLight: Boolean = this.isLight,
    ) = AppColors(
        primary = primary,
        primaryDisabled = primaryDisabled,
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
        positive = positive,
        warning = warning,
        negative = negative,
        onPrimary = onPrimary,
        onPrimaryDisabled = onPrimaryDisabled,
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
        primaryDisabled = other.primaryDisabled
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
        positive = other.positive
        warning = other.warning
        negative = other.negative
        onPrimary = other.onPrimary
        onPrimaryDisabled = other.onPrimaryDisabled
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        onSurfaceVariant = other.onSurfaceVariant
        onError = other.onError
        onStatus = other.onStatus
        isLight = other.isLight
    }
}
