package com.bunbeauty.papakarlo.feature.address.ui.auto_complete_text_field

import androidx.compose.runtime.Stable

@Stable
interface AutoCompleteEntity {
    val value: String
    fun filter(query: String): Boolean
}