package com.bunbeauty.papakarlo.common.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.common.ui.theme.color.AppColors
import com.bunbeauty.papakarlo.common.ui.theme.color.DjanDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.DjanLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.GustoPubDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.GustoPubLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.LocalAppColors
import com.bunbeauty.papakarlo.common.ui.theme.color.PapaKarloDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.PapaKarloLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.TandirHouseDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.TandirHouseLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.YuliarDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.YuliarLightColors
import com.bunbeauty.shared.Constants.DJAN_FLAVOR_NAME
import com.bunbeauty.shared.Constants.GUSTO_PUB_FLAVOR_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.TANDIR_HOUSE_FLAVOR_NAME
import com.bunbeauty.shared.Constants.VKUS_KAVKAZA_FLAVOR_NAME
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodDeliveryTheme(
    flavor: String = BuildConfig.FLAVOR,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (flavor) {
        PAPA_KARLO_FLAVOR_NAME -> {
            if (isDarkTheme) {
                PapaKarloDarkColors
            } else {
                PapaKarloLightColors
            }
        }
        YULIAR_FLAVOR_NAME -> {
            if (isDarkTheme) {
                YuliarDarkColors
            } else {
                YuliarLightColors
            }
        }
        DJAN_FLAVOR_NAME -> {
            if (isDarkTheme) {
                DjanDarkColors
            } else {
                DjanLightColors
            }
        }
        GUSTO_PUB_FLAVOR_NAME -> {
            if (isDarkTheme) {
                GustoPubDarkColors
            } else {
                GustoPubLightColors
            }
        }
        TANDIR_HOUSE_FLAVOR_NAME -> {
            if (isDarkTheme) {
                TandirHouseDarkColors
            } else {
                TandirHouseLightColors
            }
        }
        VKUS_KAVKAZA_FLAVOR_NAME -> {
            // TODO add new colors
            if (isDarkTheme) {
                TandirHouseDarkColors
            } else {
                TandirHouseLightColors
            }
        }
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
        LocalAppDimensions provides AppDimensions(),
        LocalAppTypography provides AppTypography(),
        content = content
    )
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
