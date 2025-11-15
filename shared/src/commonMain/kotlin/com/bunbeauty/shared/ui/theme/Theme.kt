package com.bunbeauty.shared.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.bunbeauty.shared.FoodDeliveryCompany
import com.bunbeauty.shared.ui.theme.color.AppColors
import com.bunbeauty.shared.ui.theme.color.DjanDarkColors
import com.bunbeauty.shared.ui.theme.color.DjanLightColors
import com.bunbeauty.shared.ui.theme.color.EmojiDarkColors
import com.bunbeauty.shared.ui.theme.color.EmojiLightColors
import com.bunbeauty.shared.ui.theme.color.EstPoestDarkColors
import com.bunbeauty.shared.ui.theme.color.EstPoestLightColors
import com.bunbeauty.shared.ui.theme.color.GustoPubDarkColors
import com.bunbeauty.shared.ui.theme.color.GustoPubLightColors
import com.bunbeauty.shared.ui.theme.color.LegendaDarkColors
import com.bunbeauty.shared.ui.theme.color.LegendaLightColors
import com.bunbeauty.shared.ui.theme.color.LimonadColors
import com.bunbeauty.shared.ui.theme.color.LimonadDarkColors
import com.bunbeauty.shared.ui.theme.color.LocalAppColors
import com.bunbeauty.shared.ui.theme.color.PapaKarloDarkColors
import com.bunbeauty.shared.ui.theme.color.PapaKarloLightColors
import com.bunbeauty.shared.ui.theme.color.TandirHouseDarkColors
import com.bunbeauty.shared.ui.theme.color.TandirHouseLightColors
import com.bunbeauty.shared.ui.theme.color.TavernaColors
import com.bunbeauty.shared.ui.theme.color.TavernaDarkColors
import com.bunbeauty.shared.ui.theme.color.UsadbaDarkColors
import com.bunbeauty.shared.ui.theme.color.UsadbaLightColors
import com.bunbeauty.shared.ui.theme.color.VkusKavkazaDarkColors
import com.bunbeauty.shared.ui.theme.color.VkusKavkazaLightColors
import com.bunbeauty.shared.ui.theme.color.VoljaneColors
import com.bunbeauty.shared.ui.theme.color.VoljaneDarkColors
import com.bunbeauty.shared.ui.theme.color.YuliarDarkColors
import com.bunbeauty.shared.ui.theme.color.YuliarLightColors
import org.jetbrains.compose.resources.DrawableResource
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.logo_medium_djan
import papakarlo.shared.generated.resources.logo_medium_emoji
import papakarlo.shared.generated.resources.logo_medium_estpoest
import papakarlo.shared.generated.resources.logo_medium_gustopub
import papakarlo.shared.generated.resources.logo_medium_papakarlo
import papakarlo.shared.generated.resources.logo_medium_tandir_house
import papakarlo.shared.generated.resources.logo_medium_taverna
import papakarlo.shared.generated.resources.logo_medium_usadba
import papakarlo.shared.generated.resources.logo_medium_vkus_kavkaza
import papakarlo.shared.generated.resources.logo_medium_voljane
import papakarlo.shared.generated.resources.logo_medium_yuliar
import papakarlo.shared.generated.resources.logo_small_djan
import papakarlo.shared.generated.resources.logo_small_emoji
import papakarlo.shared.generated.resources.logo_small_estpoest
import papakarlo.shared.generated.resources.logo_small_gustopub
import papakarlo.shared.generated.resources.logo_small_papakarlo
import papakarlo.shared.generated.resources.logo_small_tandir_house
import papakarlo.shared.generated.resources.logo_small_taverna
import papakarlo.shared.generated.resources.logo_small_usadba
import papakarlo.shared.generated.resources.logo_small_vkus_kavkaza
import papakarlo.shared.generated.resources.logo_small_voljane
import papakarlo.shared.generated.resources.logo_small_yuliar

private var BASE_THEME_FLAVOR: String = "papakarlo"
var logoMedium: DrawableResource? = null
var logoSmall: DrawableResource? = null

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodDeliveryTheme(
    flavor: String = BASE_THEME_FLAVOR,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = getAppColors(
        isDarkTheme = isDarkTheme,
        flavor = flavor
    )
    val rememberedColors = remember {
        colors.copy()
    }.apply {
        update(colors)
    }

    CompositionLocalProvider(
        LocalAppColors provides rememberedColors,
        LocalAppDimensions provides AppDimensions(),
        LocalAppTypography provides AppTypography(),
        content = content,
    )
}

private fun getAppColors(
    flavor: String,
    isDarkTheme: Boolean,
): AppColors {
    val company = FoodDeliveryCompany.getByFlavor(flavor)
    return when (company) {
        FoodDeliveryCompany.PAPA_KARLO -> {
            logoMedium = Res.drawable.logo_medium_papakarlo
            logoSmall = Res.drawable.logo_small_papakarlo
            if (isDarkTheme) {
                PapaKarloDarkColors
            } else {
                PapaKarloLightColors
            }
        }

        FoodDeliveryCompany.YULIAR -> {
            logoMedium = Res.drawable.logo_medium_yuliar
            logoSmall = Res.drawable.logo_small_yuliar
            if (isDarkTheme) {
                YuliarDarkColors
            } else {
                YuliarLightColors
            }
        }

        FoodDeliveryCompany.DJAN -> {
            logoMedium = Res.drawable.logo_medium_djan
            logoSmall = Res.drawable.logo_small_djan
            if (isDarkTheme) {
                DjanDarkColors
            } else {
                DjanLightColors
            }
        }

        FoodDeliveryCompany.GUSTO_PUB -> {
            logoMedium = Res.drawable.logo_medium_gustopub
            logoSmall = Res.drawable.logo_small_gustopub
            if (isDarkTheme) {
                GustoPubDarkColors
            } else {
                GustoPubLightColors
            }
        }

        FoodDeliveryCompany.TANDIR_HOUSE -> {
            logoMedium = Res.drawable.logo_medium_tandir_house
            logoSmall = Res.drawable.logo_small_tandir_house
            if (isDarkTheme) {
                TandirHouseDarkColors
            } else {
                TandirHouseLightColors
            }
        }

        FoodDeliveryCompany.VKUS_KAVKAZA -> {
            logoMedium = Res.drawable.logo_medium_vkus_kavkaza
            logoSmall = Res.drawable.logo_small_vkus_kavkaza
            if (isDarkTheme) {
                VkusKavkazaDarkColors
            } else {
                VkusKavkazaLightColors
            }
        }

        FoodDeliveryCompany.EST_POEST -> {
            logoMedium = Res.drawable.logo_medium_estpoest
            logoSmall = Res.drawable.logo_small_estpoest
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
            logoMedium = Res.drawable.logo_medium_usadba
            logoSmall = Res.drawable.logo_small_usadba
            if (isDarkTheme) {
                UsadbaDarkColors
            } else {
                UsadbaLightColors
            }
        }

        FoodDeliveryCompany.EMOJI -> {
            logoMedium = Res.drawable.logo_medium_emoji
            logoSmall = Res.drawable.logo_small_emoji
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

        FoodDeliveryCompany.TAVERNA -> {
            logoMedium = Res.drawable.logo_medium_taverna
            logoSmall = Res.drawable.logo_small_taverna
            if (isDarkTheme) {
                TavernaDarkColors
            } else {
                TavernaColors
            }
        }

        FoodDeliveryCompany.VOLJANE -> {
            logoMedium = Res.drawable.logo_medium_voljane
            logoSmall = Res.drawable.logo_small_voljane
            if (isDarkTheme) {
                VoljaneDarkColors
            } else {
                VoljaneColors
            }
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
