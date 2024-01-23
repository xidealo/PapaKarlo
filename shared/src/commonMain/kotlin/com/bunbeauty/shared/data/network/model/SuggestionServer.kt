package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SuggestionServer(

    @SerialName("fiasId")
    val fiasId: String,

    @SerialName("street")
    val street: String,

)