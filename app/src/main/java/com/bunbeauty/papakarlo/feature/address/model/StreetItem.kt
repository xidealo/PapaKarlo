package com.bunbeauty.papakarlo.feature.address.model

import com.bunbeauty.papakarlo.feature.address.ui.auto_complete_text_field.AutoCompleteEntity

data class StreetItem(
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