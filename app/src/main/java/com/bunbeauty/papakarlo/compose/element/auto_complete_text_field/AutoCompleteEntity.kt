package com.bunbeauty.papakarlo.compose.element.auto_complete_text_field

import androidx.compose.runtime.Stable

@Stable
interface AutoCompleteEntity {
    val value: String
    fun filter(query: String): Boolean
}