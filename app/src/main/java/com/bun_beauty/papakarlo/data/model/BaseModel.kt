package com.bun_beauty.papakarlo.data.model

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
) {
    companion object {
        const val UUID: String = "uuid"
        const val ID: String = "id"
    }
}