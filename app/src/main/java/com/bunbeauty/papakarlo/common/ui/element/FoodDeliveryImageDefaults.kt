package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryImageDefaults {

    @Composable
    fun getMediumLogoId(): Int = if (FoodDeliveryTheme.colors.isLight) {
        R.drawable.logo_medium
    } else {
        R.drawable.logo_medium_dark
    }

    @Composable
    fun getLargeLogoId(): Int = if (FoodDeliveryTheme.colors.isLight) {
        R.drawable.logo_large
    } else {
        R.drawable.logo_large_dark
    }
}
