package com.bunbeauty.papakarlo.common.ui.screen

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

@Composable
fun ErrorScreen(
    @StringRes mainTextId: Int,
    @StringRes extraTextId: Int? = null,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(
                color = FoodDeliveryTheme.colors.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Image(
            painter = painterResource(R.drawable.error),
            contentDescription = stringResource(R.string.description_empty_profile)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(id = mainTextId),
            style = FoodDeliveryTheme.typography.h2,
            color = FoodDeliveryTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        extraTextId?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(id = extraTextId),
                style = FoodDeliveryTheme.typography.body2,
                color = FoodDeliveryTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        MainButton(
            modifier = Modifier
                .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            onClick = onClick,
            textStringId = R.string.action_retry
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(
        mainTextId = R.string.common_error,
        extraTextId = R.string.internet_error
    ) {
    }
}
