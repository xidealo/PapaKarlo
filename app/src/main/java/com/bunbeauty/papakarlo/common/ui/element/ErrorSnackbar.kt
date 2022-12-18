package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        modifier = modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Card(
                shape = mediumRoundedCornerShape,
                colors = FoodDeliveryTheme.colors.cardColors()
            ) {
                Text(
                    modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                    text = snackbarData.visuals.message,
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onError
                )
            }
        }
    )
}
