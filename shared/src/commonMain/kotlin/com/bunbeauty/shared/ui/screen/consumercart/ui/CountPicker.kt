package com.bunbeauty.shared.ui.screen.consumercart.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.bunbeauty.designsystem.ui.animation.slideInAndSlideOutVerticallyWithFadeAnimation
import com.bunbeauty.designsystem.ui.element.button.FoodDeliveryButtonDefaults
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import papakarlo.shared.generated.resources.description_consumer_cart_decrease
import papakarlo.shared.generated.resources.description_consumer_cart_increase
import papakarlo.shared.generated.resources.ic_minus_16
import papakarlo.shared.generated.resources.ic_plus_16

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CountPicker(
    modifier: Modifier = Modifier,
    count: Int,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .clip(FoodDeliveryButtonDefaults.buttonShape)
                .border(
                    border = BorderStroke(2.dp, FoodDeliveryTheme.colors.mainColors.primary),
                    shape = FoodDeliveryButtonDefaults.buttonShape,
                ).background(FoodDeliveryTheme.colors.mainColors.surface)
                .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CountPickerButton(
            iconId = Res.drawable.ic_minus_16,
            descriptionStringId = Res.string.description_consumer_cart_decrease,
            onClick = onCountDecreased,
        )

        AnimatedContent(
            modifier =
                Modifier
                    .height(IntrinsicSize.Min),
            targetState = count,
            transitionSpec = {
                slideInAndSlideOutVerticallyWithFadeAnimation
            },
            label = "CountPickerCount",
        ) { countExpanded ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxHeight(),
            ) {
                Text(
                    modifier =
                        Modifier
                            .padding(horizontal = 4.dp),
                    text = countExpanded.toString(),
                    style = FoodDeliveryTheme.typography.bodySmall.bold,
                    color = FoodDeliveryTheme.colors.mainColors.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }

        CountPickerButton(
            iconId = Res.drawable.ic_plus_16,
            descriptionStringId = Res.string.description_consumer_cart_increase,
            onClick = onCountIncreased,
        )
    }
}

@Composable
fun CountPickerButton(
    iconId: DrawableResource,
    descriptionStringId: StringResource,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(36.dp),
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.iconButtonColors,
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(iconId),
            tint = FoodDeliveryTheme.colors.mainColors.primary,
            contentDescription = stringResource(descriptionStringId),
        )
    }
}

@Preview
@Composable
private fun CountPickerOneDigitPreview() {
    FoodDeliveryTheme {
        CountPicker(
            count = 5,
            onCountIncreased = {},
            onCountDecreased = {},
        )
    }
}

@Preview
@Composable
private fun CountPickerTwoDigitsPreview() {
    FoodDeliveryTheme {
        CountPicker(
            count = 99,
            onCountIncreased = {},
            onCountDecreased = {},
        )
    }
}
