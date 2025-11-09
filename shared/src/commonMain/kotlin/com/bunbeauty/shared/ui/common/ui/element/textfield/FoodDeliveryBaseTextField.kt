package com.bunbeauty.shared.ui.common.ui.element.textfield


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.common.ui.element.CircularProgressBar
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import org.jetbrains.compose.resources.StringResource
import papakarlo.shared.generated.resources.hint_create_order_comment
import papakarlo.shared.generated.resources.ic_address
import papakarlo.shared.generated.resources.ic_clear

@Composable
fun FoodDeliveryBaseTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    labelStringId: StringResource,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (value: String) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    isError: Boolean = false,
    isLoading: Boolean = false,
    trailingIcon: (@Composable () -> Unit)? = null,
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
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingIcon = {
            if (trailingIcon == null) {
                TrailingIcon(
                    isLoading = isLoading,
                    textValue = value,
                    onClick = {
                        onValueChange("")
                    },
                )
            } else {
                trailingIcon()
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        colors = FoodDeliveryTextFieldDefaults.textFieldColors,
    )
}

@Composable
fun FoodDeliveryBaseTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    labelStringId: StringResource,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (value: TextFieldValue) -> Unit,
    maxSymbols: Int = Int.MAX_VALUE,
    maxLines: Int = 1,
    isError: Boolean = false,
    isLoading: Boolean = false,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides FoodDeliveryTextFieldDefaults.textSelectionColors,
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = { changedValue ->
                onValueChange(
                    changedValue.copy(
                        text = changedValue.text.take(maxSymbols),
                    ),
                )
            },
            textStyle = FoodDeliveryTheme.typography.bodyLarge,
            label = {
                Text(
                    text = stringResource(labelStringId),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingIcon = {
                if (trailingIcon == null) {
                    TrailingIcon(
                        isLoading = isLoading,
                        textValue = value.text,
                        onClick = {
                            onValueChange(TextFieldValue(""))
                        },
                    )
                } else {
                    trailingIcon()
                }
            },
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = maxLines == 1,
            maxLines = maxLines,
            colors = FoodDeliveryTextFieldDefaults.textFieldColors,
        )
    }
}

@Composable
private fun TrailingIcon(
    isLoading: Boolean,
    textValue: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        isLoading -> {
            CircularProgressBar(modifier = modifier.size(16.dp))
        }

        textValue.isNotEmpty() -> {
            Icon(
                modifier =
                    modifier
                        .size(16.dp)
                        .clickable(onClick = onClick),
                painter = painterResource(Res.drawable.ic_clear),
                tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun FoodDeliveryTextBaseFieldPreview() {
    FoodDeliveryTheme {
        FoodDeliveryBaseTextField(
            value = "Нужно больше еды \n ...",
            labelStringId = Res.string.hint_create_order_comment,
            onValueChange = {},
        )
    }
}

@Preview
@Composable
private fun FoodDeliveryTextBaseFieldWithLoadingPreview() {
    FoodDeliveryTheme {
        FoodDeliveryBaseTextField(
            value = "Нужно больше еды \n ...",
            labelStringId = Res.string.hint_create_order_comment,
            onValueChange = {},
            isLoading = true,
        )
    }
}

@Preview
@Composable
private fun FoodDeliveryTextBaseFieldWithErrorPreview() {
    FoodDeliveryTheme {
        FoodDeliveryBaseTextField(
            value = "Нужно больше еды \n ...",
            labelStringId = Res.string.hint_create_order_comment,
            onValueChange = {},
            isError = true,
        )
    }
}

@Preview
@Composable
private fun FoodDeliveryTextBaseFieldWithTrailingIconPreview() {
    FoodDeliveryTheme {
        FoodDeliveryBaseTextField(
            value = "Нужно больше еды \n ...",
            labelStringId = Res.string.hint_create_order_comment,
            onValueChange = {},
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_address),
                    contentDescription = null,
                )
            },
        )
    }
}
