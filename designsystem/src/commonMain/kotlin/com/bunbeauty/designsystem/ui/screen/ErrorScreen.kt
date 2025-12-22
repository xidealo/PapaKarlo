package com.bunbeauty.designsystem.ui.screen


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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.button.MainButton
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_retry
import papakarlo.designsystem.generated.resources.ic_error
import papakarlo.designsystem.generated.resources.preview_string

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
            mainTextId = Res.string.preview_string,
            extraTextId = Res.string.preview_string,
            onClick = {},
        )
    }
}
