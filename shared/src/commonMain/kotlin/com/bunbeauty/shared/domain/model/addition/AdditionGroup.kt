package com.bunbeauty.shared.domain.model.addition

data class AdditionGroup(
    val additionList: List<Addition>,
    val isVisible: Boolean,
    val name: String,
    val singleChoice: Boolean,
    val uuid: String,
    val priority: Int
)
