package com.bunbeauty.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MenuProduct(
    @PrimaryKey
    override var uuid: String = "",
    val name: String = "",
    val cost: Int = 0,
    val discountCost: Int? = null,
    val weight: Int = 0,
    val description: String = "",
    val comboDescription: String = "",
    val photoLink: String = "",
    val onFire: Boolean = false,
    val inOven: Boolean = false,
    val productCode: String = "",
    val barcode: Int = 0,
    val visible: Boolean = true
) : BaseModel, Parcelable