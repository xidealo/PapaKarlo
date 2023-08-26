package com.bunbeauty.papakarlo.common.ui.element.text_field

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.applyIfNotNull
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun FoodDeliveryTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    @StringRes labelStringId: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (value: String) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    @StringRes errorMessageId: Int? = null
) {
    Column(modifier = modifier) {
        FoodDeliveryBaseTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            labelStringId = labelStringId,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onValueChange = onValueChange,
            maxSymbols = maxSymbols,
            maxLines = maxLines,
            isError = errorMessageId != null
        )
        errorMessageId?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp),
                text = stringResource(errorMessageId),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.error
            )
        }
    }
}

@Composable
fun FoodDeliveryTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
    value: TextFieldValue = TextFieldValue(""),
    @StringRes labelStringId: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (value: TextFieldValue) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    @StringRes errorMessageId: Int? = null
) {
    Column(modifier = modifier) {
        FoodDeliveryBaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .applyIfNotNull(focusRequester) {
                    focusRequester(it)
                },
            value = value,
            labelStringId = labelStringId,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onValueChange = onValueChange,
            maxSymbols = maxSymbols,
            maxLines = maxLines,
            isError = errorMessageId != null
        )
        errorMessageId?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp),
                text = stringResource(errorMessageId),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.error
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun FoodDeliveryTextFieldPreview() {
    FoodDeliveryTextField(
        value = "Нужно больше еды \n ...",
        labelStringId = R.string.hint_create_order_comment,
        onValueChange = {}
    )
}
