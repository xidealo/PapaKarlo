package com.bunbeauty.papakarlo.feature.address.ui.auto_complete_text_field

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.bunbeauty.papakarlo.common.ui.element.EditText
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextType
import com.bunbeauty.shared.presentation.create_address.AutoCompleteEntity

@Composable
fun <T : AutoCompleteEntity> AutoCompleteEditText(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue = TextFieldValue(""),
    @StringRes labelStringId: Int,
    editTextType: EditTextType,
    isLast: Boolean = false,
    maxLines: Int = 1,
    focus: Boolean = false,
    @StringRes errorMessageId: Int? = null,
    list: List<T>,
    onTextChanged: (TextFieldValue) -> Unit,
) {
    var listState by remember { mutableStateOf(emptyList<T>()) }
    Column(modifier = modifier) {
        EditText(
            modifier = Modifier.fillMaxWidth(),
            textFieldValue = textFieldValue,
            labelStringId = labelStringId,
            editTextType = editTextType,
            isLast = isLast,
            maxLines = maxLines,
            focus = focus,
            errorMessageId = errorMessageId,
            onTextChanged = { changedValue ->
                onTextChanged(changedValue)
                listState = if (changedValue.text.isEmpty()) {
                    emptyList()
                } else {
                    list.filter { autoCompleteEntity ->
                        autoCompleteEntity.filter(changedValue.text)
                    }.take(3)
                }
            }
        )
        if (listState.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                listState.forEachIndexed { i, item ->
                    DropdownItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        text = item.value,
                        onClick = {
                            onTextChanged(
                                TextFieldValue(
                                    text = item.value,
                                    selection = TextRange(item.value.length)
                                )
                            )
                            listState = emptyList()
                        }
                    )
                }
            }
        }
    }
}
