package com.bunbeauty.shared.ui.common.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.common.ui.element.button.MainButton
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.bold
import org.jetbrains.compose.resources.StringResource
import papakarlo.shared.generated.resources.action_retry
import papakarlo.shared.generated.resources.common_error
import papakarlo.shared.generated.resources.ic_error
import papakarlo.shared.generated.resources.internet_error

@Composable
fun ErrorScreen(
    mainTextId: StringResource,
    extraTextId: StringResource? = null,
    onClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier =
                Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        FoodDeliveryTheme.colors.statusColors.negative,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                painter = painterResource(
                    Res.drawable.ic_error
                ),
                tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                contentDescription = null,
            )
        }
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(resource = mainTextId),
            style = FoodDeliveryTheme.typography.titleMedium.bold,
            color = FoodDeliveryTheme.colors.mainColors.onSurface,
            textAlign = TextAlign.Center,
        )
        extraTextId?.let {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                text = stringResource(resource = extraTextId),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            modifier =
                Modifier
                    .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            onClick = onClick,
            textStringId = Res.string.action_retry,
        )
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    FoodDeliveryTheme {
        ErrorScreen(
            mainTextId = Res.string.common_error,
            extraTextId = Res.string.internet_error,
            onClick = {},
        )
    }
}
