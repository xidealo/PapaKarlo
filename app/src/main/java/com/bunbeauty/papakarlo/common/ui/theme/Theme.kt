package com.bunbeauty.papakarlo.common.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodDeliveryTheme(
    flavor: String = BuildConfig.FLAVOR,
    content: @Composable () -> Unit
) {
    val colors = when (flavor) {
        PAPA_KARLO_FLAVOR_NAME -> PapaKarloColors
        YULIAR_FLAVOR_NAME -> YuliarColors
        else -> throw UnknownFlavorException()
    }
    val rememberedColors = remember {
        colors.copy()
    }.apply {
        update(colors)
    }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        LocalAppColors provides rememberedColors,
        LocalRippleTheme provides FoodDeliveryRippleTheme,
        LocalAppDimensions provides AppDimensions(),
        LocalAppTypography provides AppTypography(),
        content = content
    )
}

private object FoodDeliveryRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = FoodDeliveryTheme.colors.mainColors.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.1f, 0.1f, 0.1f, 0.1f)
}

object FoodDeliveryTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimensions.current
}
