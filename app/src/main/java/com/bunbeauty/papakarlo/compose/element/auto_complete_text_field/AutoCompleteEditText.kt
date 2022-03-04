package com.bunbeauty.papakarlo.compose.element.auto_complete_text_field

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.bunbeauty.papakarlo.compose.element.EditText
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType

@Composable
fun <T : AutoCompleteEntity> AutoCompleteEditText(
    modifier: Modifier = Modifier,
    initTextFieldValue: TextFieldValue = TextFieldValue(""),
    @StringRes labelStringId: Int,
    editTextType: EditTextType,
    maxLines: Int = 1,
    focus: Boolean = false,
    list: List<T>,
    onTextChanged: (TextFieldValue) -> Unit,
) {
    var listState by remember { mutableStateOf(emptyList<T>()) }
    Column(modifier = modifier) {
        EditText(
            modifier = Modifier.fillMaxWidth(),
            initTextFieldValue = initTextFieldValue,
            labelStringId = labelStringId,
            editTextType = editTextType,
            maxLines = maxLines,
            focus = focus,
            onTextChanged = { value ->
                onTextChanged(value)
                listState = if (value.text.isEmpty()) {
                    emptyList()
                } else {
                    list.filter { autoCompleteEntity ->
                        autoCompleteEntity.filter(value.text)
                    }.take(3)
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            itemsIndexed(listState) { i, item ->
                DropdownItem(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.getTopItemSpaceByIndex(i)
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