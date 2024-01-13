package com.bunbeauty.papakarlo.common.ui.element.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun FoodDeliveryBaseTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    @StringRes labelStringId: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (value: String) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    isError: Boolean = false,
    isLoading: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { changedValue ->
            onValueChange(changedValue.take(maxSymbols))
        },
        textStyle = FoodDeliveryTheme.typography.bodyLarge,
        label = {
            Text(
                text = stringResource(labelStringId),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = {
            if (isLoading) {
                CircularProgressBar(modifier = Modifier.size(16.dp))
            } else if (value.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .icon16()
                        .clickable {
                            onValueChange("")
                        },
                    painter = painterResource(R.drawable.ic_clear),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = null
                )
            }
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        colors = FoodDeliveryTextFieldDefaults.textFieldColors
    )
}

@Composable
fun FoodDeliveryBaseTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    @StringRes labelStringId: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (value: TextFieldValue) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    isError: Boolean = false,
    isLoading: Boolean = false,
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides FoodDeliveryTextFieldDefaults.textSelectionColors
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = { changedValue ->
                onValueChange(
                    changedValue.copy(
                        text = changedValue.text.take(maxSymbols)
                    )
                )
            },
            textStyle = FoodDeliveryTheme.typography.bodyLarge,
            label = {
                Text(
                    text = stringResource(labelStringId),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            trailingIcon = {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else if (value.text.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .icon16()
                            .clickable {
                                onValueChange(TextFieldValue(""))
                            },
                        painter = painterResource(R.drawable.ic_clear),
                        tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        contentDescription = null
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            singleLine = maxLines == 1,
            maxLines = maxLines,
            colors = FoodDeliveryTextFieldDefaults.textFieldColors
        )
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun FoodDeliveryTextBaseFieldPreview() {
    FoodDeliveryBaseTextField(
        value = "Нужно больше еды \n ...",
        labelStringId = R.string.hint_create_order_comment,
        onValueChange = {}
    )
}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun FoodDeliveryTextBaseFieldWithLoadingPreview() {
    FoodDeliveryBaseTextField(
        value = "Нужно больше еды \n ...",
        labelStringId = R.string.hint_create_order_comment,
        onValueChange = {},
        isLoading = true,
    )
}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun FoodDeliveryTextBaseFieldWithErrorPreview() {
    FoodDeliveryBaseTextField(
        value = "Нужно больше еды \n ...",
        labelStringId = R.string.hint_create_order_comment,
        onValueChange = {},
        isError = true,
    )
}
