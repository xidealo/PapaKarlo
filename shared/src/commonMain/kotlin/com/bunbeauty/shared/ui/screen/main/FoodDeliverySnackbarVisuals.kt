package com.bunbeauty.shared.ui.screen.main

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class FoodDeliverySnackbarVisuals(
    val foodDeliveryMessage: FoodDeliveryMessage,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
) : SnackbarVisuals {
    override val message: String = foodDeliveryMessage.text
}
