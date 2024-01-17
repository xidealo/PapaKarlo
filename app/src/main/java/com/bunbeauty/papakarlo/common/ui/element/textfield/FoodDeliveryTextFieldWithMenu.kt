package com.bunbeauty.papakarlo.common.ui.element.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.presentation.SuggestionUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryTextFieldWithMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSuggestionClick: (suggestion: SuggestionUi) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    @StringRes labelStringId: Int,
    onValueChange: (value: String) -> Unit,
    @StringRes errorMessageId: Int? = null,
    suggestionsList: ImmutableList<SuggestionUi> = persistentListOf(),
    isLoading: Boolean = false
) {
    Column {
        ExposedDropdownMenuBox(
            modifier = modifier,
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            FoodDeliveryBaseTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = value,
                labelStringId = labelStringId,
                onValueChange = onValueChange,
                isError = errorMessageId != null,
                isLoading = isLoading
            )

            if (suggestionsList.isNotEmpty()) {
                DropdownMenu(
                    modifier = Modifier
                        .background(FoodDeliveryTheme.colors.mainColors.surface)
                        .exposedDropdownSize(),
                    expanded = expanded,
                    properties = PopupProperties(
                        focusable = false,
                        dismissOnClickOutside = true,
                        dismissOnBackPress = true
                    ),
                    onDismissRequest = {}
                ) {
                    suggestionsList.forEach { suggestion ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = suggestion.value,
                                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                                    style = FoodDeliveryTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                onSuggestionClick(suggestion)
                            }
                        )
                    }
                }
            }
        }
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
