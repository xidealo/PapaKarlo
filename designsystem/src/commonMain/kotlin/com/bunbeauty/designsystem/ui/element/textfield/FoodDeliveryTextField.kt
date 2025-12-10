package com.bunbeauty.designsystem.ui.element.textfield


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.applyIfNotNull
import com.bunbeauty.designsystem.ui.element.textfield.FoodDeliveryTextFieldDefaults.keyboardActionsDefault
import com.bunbeauty.designsystem.ui.element.textfield.FoodDeliveryTextFieldDefaults.keyboardOptionsDefault
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.hint_create_order_comment

@Composable
fun FoodDeliveryTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
    value: String = "",
    labelStringId: StringResource,
    keyboardOptions: KeyboardOptions = keyboardOptionsDefault(),
    keyboardActions: KeyboardActions = keyboardActionsDefault(),
    onValueChange: (value: String) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    errorMessageStringId: StringResource? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Column(modifier = modifier) {
        FoodDeliveryBaseTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .applyIfNotNull(focusRequester) {
                        focusRequester(it)
                    },
            value = value,
            labelStringId = labelStringId,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange,
            maxSymbols = maxSymbols,
            maxLines = maxLines,
            isError = errorMessageStringId != null,
            trailingIcon = trailingIcon,
        )
        errorMessageStringId?.let {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp),
                text = stringResource(errorMessageStringId),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.error,
            )
        }
    }
}

@Composable
fun FoodDeliveryTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
    value: TextFieldValue = TextFieldValue(""),
    labelStringId: StringResource,
    keyboardOptions: KeyboardOptions = keyboardOptionsDefault(),
    keyboardActions: KeyboardActions = keyboardActionsDefault(),
    onValueChange: (value: TextFieldValue) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    errorMessageId: StringResource? = null,
) {
    Column(modifier = modifier) {
        FoodDeliveryBaseTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .applyIfNotNull(focusRequester) {
                        focusRequester(it)
                    },
            value = value,
            labelStringId = labelStringId,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange,
            maxSymbols = maxSymbols,
            maxLines = maxLines,
            isError = errorMessageId != null,
        )
        errorMessageId?.let {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp),
                text = stringResource(errorMessageId),
                style = FoodDeliveryTheme.typography.bodySmall,
                color = FoodDeliveryTheme.colors.mainColors.error,
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
        labelStringId = Res.string.hint_create_order_comment,
        onValueChange = {},
    )
}
