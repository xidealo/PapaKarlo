package com.bunbeauty.data.model

import androidx.room.Ignore

abstract class BaseModel(
    /**
     * Local id
     */
    @Transient
    @Ignore
    open var id: Long = 0,

    /**
     * API id
     */
    @Transient
    @Ignore
    open var uuid: String = ""
)