package com.bunbeauty.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
data class ProductUi(
    val key: String,
    val uuid: String,
    val photoLink: String,
    val name: String,
    val oldPrice: String?,
    val newPrice: String,
)
