package com.bunbeauty.shared.domain.model.link

data class Link(
    val uuid: String,
    val type: LinkType,
    val linkValue: String,
)