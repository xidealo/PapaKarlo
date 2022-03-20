package com.bunbeauty.papakarlo.feature.address.create_address

import com.bunbeauty.papakarlo.compose.element.auto_complete_text_field.AutoCompleteEntity

data class StreetItemModel(
    val uuid: String,
    val name: String,
    val cityUuid: String,
) : AutoCompleteEntity {

    override val value: String = name

    override fun filter(query: String): Boolean {
        return query.lowercase().split(" ").all { queryPart ->
            name.lowercase().split(" ").any { namePart ->
                namePart.startsWith(queryPart)
            }
        }
    }
}