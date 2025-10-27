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
import com.bunbeauty.papakarlo.common.ui.theme.color.EmojiDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.EmojiLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.EstPoestDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.EstPoestLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.GustoPubDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.GustoPubLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.LegendaDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.LegendaLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.LimonadColors
import com.bunbeauty.papakarlo.common.ui.theme.color.LimonadDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.LocalAppColors
import com.bunbeauty.papakarlo.common.ui.theme.color.PapaKarloDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.PapaKarloLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.TandirHouseDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.TandirHouseLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.TavernaColors
import com.bunbeauty.papakarlo.common.ui.theme.color.TavernaDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.UsadbaDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.UsadbaLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.VkusKavkazaDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.VkusKavkazaLightColors
import com.bunbeauty.papakarlo.common.ui.theme.color.VoljaneColors
import com.bunbeauty.papakarlo.common.ui.theme.color.VoljaneDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.YuliarDarkColors
import com.bunbeauty.papakarlo.common.ui.theme.color.YuliarLightColors
import com.bunbeauty.shared.FoodDeliveryCompany

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodDeliveryTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = getAppColors(isDarkTheme = isDarkTheme)
    val rememberedColors =
        remember {
            colors.copy()
        }.apply {
            update(colors)
        }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        LocalAppColors provides rememberedColors,
        LocalAppDimensions provides AppDimensions(),
        LocalAppTypography provides AppTypography(),
        content = content,
    )
}

private fun getAppColors(
    flavor: String = BuildConfig.FLAVOR,
    isDarkTheme: Boolean,
): AppColors {
    val company = FoodDeliveryCompany.getByFlavor(flavor)
    return when (company) {
        FoodDeliveryCompany.PAPA_KARLO -> {
            if (isDarkTheme) {
                PapaKarloDarkColors
            } else {
                PapaKarloLightColors
            }
        }

        FoodDeliveryCompany.YULIAR -> {
            if (isDarkTheme) {
                YuliarDarkColors
            } else {
                YuliarLightColors
            }
        }

        FoodDeliveryCompany.DJAN -> {
            if (isDarkTheme) {
                DjanDarkColors
            } else {
                DjanLightColors
            }
        }

        FoodDeliveryCompany.GUSTO_PUB -> {
            if (isDarkTheme) {
                GustoPubDarkColors
            } else {
                GustoPubLightColors
            }
        }

        FoodDeliveryCompany.TANDIR_HOUSE -> {
            if (isDarkTheme) {
                TandirHouseDarkColors
            } else {
                TandirHouseLightColors
            }
        }

        FoodDeliveryCompany.VKUS_KAVKAZA -> {
            if (isDarkTheme) {
                VkusKavkazaDarkColors
            } else {
                VkusKavkazaLightColors
            }
        }

        FoodDeliveryCompany.EST_POEST -> {
            if (isDarkTheme) {
                EstPoestDarkColors
            } else {
                EstPoestLightColors
            }
        }

        FoodDeliveryCompany.LEGENDA -> {
            if (isDarkTheme) {
                LegendaDarkColors
            } else {
                LegendaLightColors
            }
        }

        FoodDeliveryCompany.USADBA -> {
            if (isDarkTheme) {
                UsadbaDarkColors
            } else {
                UsadbaLightColors
            }
        }

        FoodDeliveryCompany.EMOJI -> {
            if (isDarkTheme) {
                EmojiDarkColors
            } else {
                EmojiLightColors
            }
        }

        FoodDeliveryCompany.LIMONAD ->
            if (isDarkTheme) {
                LimonadDarkColors
            } else {
                LimonadColors
            }

        FoodDeliveryCompany.TAVERNA ->
            if (isDarkTheme) {
                TavernaDarkColors
            } else {
                TavernaColors
            }

        FoodDeliveryCompany.VOLJANE ->
            if (isDarkTheme) {
                VoljaneDarkColors
            } else {
                VoljaneColors
            }
    }
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
