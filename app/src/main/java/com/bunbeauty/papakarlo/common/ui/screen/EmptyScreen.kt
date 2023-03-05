package com.bunbeauty.papakarlo.common.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
internal fun EmptyScreen(
    @DrawableRes imageId: Int,
    @StringRes imageDescriptionId: Int,
    @StringRes textId: Int,
    @StringRes buttonTextId: Int? = null,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(FoodDeliveryTheme.dimensions.mediumSpace)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = stringResource(imageDescriptionId)
            )
            Text(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(textId),
                textAlign = TextAlign.Center,
                style = FoodDeliveryTheme.typography.body1
            )
        }
        buttonTextId?.let {
            MainButton(
                modifier = Modifier.align(Alignment.BottomCenter),
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
        textId = R.string.msg_consumer_cart_empty,
        buttonTextId = R.string.action_consumer_cart_menu,
    )
}
