package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int? = null,
    text: String? = null,
    hasShadow: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        Card(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.buttonSize)
                .fillMaxHeight()
                .fillMaxWidth()
                .shadow(
                    elevation = FoodDeliveryTheme.dimensions.getEvaluation(hasShadow),
                    shape = mediumRoundedCornerShape
                )
                .clip(mediumRoundedCornerShape)
                .clickable(
                    enabled = isEnabled,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                ),
            colors = FoodDeliveryTheme.colors.mainButtonCardColors(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                        vertical = FoodDeliveryTheme.dimensions.smallSpace
                    )
            ) {
                val buttonText = text ?: textStringId?.let {
                    stringResource(it)
                } ?: ""
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = buttonText,
                    style = FoodDeliveryTheme.typography.button,
                    color = if (isEnabled) {
                        FoodDeliveryTheme.colors.onPrimary
                    } else {
                        FoodDeliveryTheme.colors.onPrimaryDisabled
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainButtonPreview() {
    MainButton(textStringId = R.string.action_login_continue) {}
}

@Preview
@Composable
private fun MainButtonDisabledPreview() {
    MainButton(
        textStringId = R.string.action_login_continue,
        isEnabled = false
    ) {}
}
