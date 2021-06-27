package com.bunbeauty.domain.model.address

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

/*
indices = [Index(unique = true, value = ["userId"])],
foreignKeys = [ForeignKey(
entity = User::class,
parentColumns = ["userId"],
childColumns = ["user_address_user_id"],
onDelete = ForeignKey.CASCADE
)]
*/

@Parcelize
@Entity
data class UserAddress(
    @PrimaryKey
    override var uuid: String = "",
    var userId: String? = null,
) : Address(), Parcelable
