package com.bunbeauty.papakarlo.compose.element

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

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
                backgroundColor = FoodDeliveryTheme.colors.error
            ) {
                Text(
                    modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                    text = snackbarData.message,
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onError
                )
            }
        }
    )
}