package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryTheme.colors.mainButtonColors(),
        shape = mediumRoundedCornerShape,
        elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow),
        enabled = isEnabled
    ) {
        val buttonText = text ?: textStringId?.let {
            stringResource(it)
        } ?: ""
        Text(
            text = buttonText,
            style = FoodDeliveryTheme.typography.button,
        )
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
