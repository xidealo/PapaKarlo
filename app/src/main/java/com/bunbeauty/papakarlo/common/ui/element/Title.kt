package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun Title(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int? = null,
    titleText: String = "",
) {
    val text = textStringId?.let { id ->
        stringResource(id)
    } ?: titleText
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        text = text,
        style = FoodDeliveryTheme.typography.h2,
        textAlign = TextAlign.Center
    )
}
