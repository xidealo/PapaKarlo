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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_cart_24
import papakarlo.designsystem.generated.resources.preview_string

@Composable
fun EmptyScreen(
    imageId: DrawableResource,
    imageDescriptionId: StringResource,
    mainTextId: StringResource,
    extraTextId: StringResource,
    buttonTextId: StringResource? = null,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            FoodDeliveryTheme.colors.mainColors.primary.copy(
                                alpha = 0.6f,
                            ),
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(resource = imageId),
                    tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                    contentDescription = stringResource(imageDescriptionId),
                )
            }
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .padding(horizontal = 16.dp),
                text = stringResource(resource = mainTextId),
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                textAlign = TextAlign.Center,
            )
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
        buttonTextId?.let {
            MainButton(
                textStringId = buttonTextId,
            ) {
                onClick?.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun EmptyScreenPreview() {
    EmptyScreen(
        imageId = Res.drawable.ic_cart_24,
        imageDescriptionId = Res.string.preview_string,
        mainTextId = Res.string.preview_string,
        extraTextId = Res.string.preview_string,
        buttonTextId = Res.string.preview_string,
    )
}
