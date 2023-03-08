package com.bunbeauty.papakarlo.common.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold

@Composable
internal fun EmptyScreen(
    @DrawableRes imageId: Int,
    @StringRes imageDescriptionId: Int,
    @StringRes mainTextId: Int,
    @StringRes extraTextId: Int,
    @StringRes buttonTextId: Int? = null,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FoodDeliveryTheme.colors.background)
            .padding(FoodDeliveryTheme.dimensions.mediumSpace)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = stringResource(imageDescriptionId)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(id = mainTextId),
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(id = extraTextId),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        buttonTextId?.let {
            MainButton(
                textStringId = buttonTextId
            ) {
                onClick?.invoke()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EmptyScreenPreview() {
    EmptyScreen(
        imageId = R.drawable.empty_cart,
        imageDescriptionId = R.string.description_consumer_cart_empty,
        mainTextId = R.string.msg_consumer_cart_empty,
        extraTextId = R.string.msg_consumer_cart_empty,
        buttonTextId = R.string.action_consumer_cart_menu,
    )
}
