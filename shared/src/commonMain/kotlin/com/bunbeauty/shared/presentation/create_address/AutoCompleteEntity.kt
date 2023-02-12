package com.bunbeauty.shared.presentation.create_address


interface AutoCompleteEntity {
    val value: String
    fun filter(query: String): Boolean
}
