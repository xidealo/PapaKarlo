package com.bunbeauty.profile.ui.screen.feedback.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class LinkUI(
    val uuid: String,
    val labelId: StringResource?,
    val iconId: DrawableResource,
    val value: String,
)
